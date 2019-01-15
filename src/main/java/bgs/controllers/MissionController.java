package bgs.controllers;

import bgs.info.MissionInfo;
import bgs.info.ReportInfo;
import bgs.info.SupportRequestInfo;
import bgs.info.TargetInfo;
import bgs.model.*;
import bgs.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class MissionController {
    @Autowired
    LoginManager manager;
    @Autowired
    MissionRepository missions;
    @Autowired
    TeamRepository teams;
    @Autowired
    TargetRepository targets;
    @Autowired
    MissionTypeRepository types;
    @Autowired
    WeaponRepository weapons;
    @Autowired
    TransportRepository transport;
    @Autowired
    PersonRepository people;
    @Autowired
    SupportRequestRepository req;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    PortraitRepository portraitRepository;
    @Autowired
    EmailService mail;
    @Autowired
    AgentRepository agents;

    /**
     * Returns 10 active targets
     * @param page Page index
     * @return Stream of target info
     */
    @RequestMapping("/targets")
    public Stream<TargetInfo> getTargets(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "page", defaultValue = "-1") int id){
        if(id >= 0)
            return Stream.of(targets.findById(id)).map(TargetInfo::new);
        return targets.findAllActive().stream().skip(page*10).limit(10).map(TargetInfo::new);
    }

    /**
     * Returns 10 active missions
     * @param page Page index
     * @return Stream of mission info
     */
    @RequestMapping("/missions")
    public Stream<MissionInfo> getMissions(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "page", defaultValue = "-1") int id){
        if(id >= 0)
            return Stream.of(missions.findById(id)).map(q -> new MissionInfo(q, teams));
        return missions.findUnfinished().stream().skip(page * 10).limit(10).map(q -> new MissionInfo(q, teams));
    }

    /**
     * Creates a new mission
     * @param id Target ID
     * @param level Access level
     * @param description Description of a mission
     * @param type Type of a mission
     * @return Success
     */
    @RequestMapping(path = "/missions/create", method = RequestMethod.POST)
    public boolean create(
            @RequestParam("id") int id,
            @RequestParam("level") int level,
            @RequestParam("desc") String description,
            @RequestParam("type") int type){
        Target t = targets.findById(id);
        if(t == null)
            return false;
        Mission m = new Mission(manager.getCurrentAgent(), t, level, description, types.findById(type));
        missions.save(m);
        return true;
    }

    /**
     * Apply for a mission
     * @param id ID of a mission
     * @param wp Your weapon
     * @param tr Your transport, if present
     * @param cov Your cover, if present
     * @return Success
     */
    @RequestMapping(path = "/missions/apply", method = RequestMethod.POST)
    public boolean apply(
            @RequestParam("id") int id,
            @RequestParam("weapon") int wp,
            @RequestParam(value = "transport", defaultValue = "-1") int tr,
            @RequestParam(value = "over", defaultValue = "-1") int cov){
        Mission m = missions.findById(id);
        if(m == null)
            return false;
        Weapon w = weapons.findById(wp);
        if(w == null)
            return false;
        if(w.getReady() == 0)
            return false;
        Transport t = transport.findById(tr);
        Person cover = people.findById(cov);
        Agent cur = manager.getCurrentAgent();

        Portrait pt = portraitRepository.findByAgent(cur);
        MissionType mt = m.getType();

        if(!(mt.getCharisma() >= pt.getCharisma()
                && mt.getLoyalty() >= pt.getLoyalty()
                && mt.getAggression() >= pt.getAggression())
        ) return false;



        Team tm = new Team(cur, m, w, t, cover);
        teams.save(tm);
        w.decReady();
        weapons.save(w);
        if(t != null){
            t.decReady();
            transport.save(t);
        }
        mail.sendMail("Agent applied for a mission", m.getResponsible(),
                String.format("Agent %s (%d) applied for a mission %d", cur.getName(), cur.getId(), m.getId()));
        mail.sendMail("You have applied for a mission", cur,
                String.format("You have applied for a mission %d, your boss is %s (%d)",
                        m.getId(),
                        m.getResponsible().getName(),
                        m.getResponsible().getId()
                )
        );
        return true;
    }

    /**
     * Start a pending mission
     * @param id ID of a mission
     * @return success
     */
    @RequestMapping(path = "/missions/start", method = RequestMethod.POST)
    public boolean start(@RequestParam("id") int id){
        Mission m = missions.findById(id);
        if(m.getStatus().equals("Выполняется") || m.getStatus().equals("Выполнена"))
            return false;
        MissionType mt = m.getType();

        if(mt.getSize() > m.getTeam().size())
            return false;

        m.setStatus("Выполняется");
        missions.save(m);

        mail.sendMail("Your mission have started", m.getResponsible(),
                String.format("Your mission (%d) have started", m.getId()));
        for (Team t :
                m.getTeam()) {
            Agent a = t.getAgent();
            mail.sendMail("Your mission have started", a,
                    String.format("Your mission (%d) have started", m.getId()));
        }

        return true;
    }

    /**
     * Update status of a mission
     * @param id ID of a mission
     * @param status New status
     * @return success
     */
    @RequestMapping(path = "/missions/update", method = RequestMethod.POST)
    public boolean updateStatus(@RequestParam("id") int id, @RequestParam("status") String status){
        Mission m = missions.findById(id);
        if(m == null)
            return false;
        m.setStatus(status);
        missions.save(m);

        mail.sendMail("Your mission status have been updated", m.getResponsible(),
                String.format("Your mission (%d) status have been updated: %s", m.getId(), status));
        for (Team t :
                m.getTeam()) {
            Agent a = t.getAgent();
            mail.sendMail("Your mission status have been updated", a,
                    String.format("Your mission (%d) status have been updated: %s", m.getId(), status));
        }
        return true;
    }

    /**
     * Send support request
     * @param id Mission ID
     * @param data Needed info
     * @param sol Soldiers count
     * @param tr Transport ID
     * @param wp Weapon ID
     * @return success
     */
    @RequestMapping(path = "/missions/support/apply", method = RequestMethod.POST)
    public boolean requestSupport(
            @RequestParam("id") int id,
            @RequestParam(name = "data", defaultValue = "", required = false) String data,
            @RequestParam(name = "soldiers", defaultValue = "0", required = false) int sol,
            @RequestParam(name = "transport", defaultValue = "-1", required = false) int tr,
            @RequestParam(name = "weapon", defaultValue = "-1", required = false) int wp){
        Mission m = missions.findById(id);
        if(m == null)
            return false;
        SupportRequest r = new SupportRequest(m, data, sol, transport.findById(tr), weapons.findById(wp));
        req.save(r);
        return true;
    }

    /**
     * Returns 10 active support request
     * @param page Page index
     * @return Stream of request info
     */
    @RequestMapping("/missions/support/process")
    public Stream<SupportRequestInfo> getSupportRequests(@RequestParam(name = "page", defaultValue = "0", required = false) int page){
        return req.findAllBySeenIsFalse().stream().skip(page*10).limit(10).map(SupportRequestInfo::new);
    }

    /**
     * Set support request status to 'processed'
     * @param id ID of request
     */
    @RequestMapping(path = "/missions/support/send", method = RequestMethod.POST)
    public void sendSupport(@RequestParam("id") int id){
        SupportRequest r = req.findById(id);
        r.setSeen();
        req.save(r);
    }

    /**
     * Show mission's reports
     * @param id ID of a mission
     * @return Stream of report info
     */
    @RequestMapping("/missions/reports")
    public Stream<ReportInfo> getReport(@RequestParam("id") int id){
        return reportRepository.findAllByMission(missions.findById(id)).stream().map(ReportInfo::new);
    }


    /**
     * Write mission report
     * @param m Mission ID
     * @param s Subject ID
     * @param name Title of report
     * @param desc Description of report
     * @param purp Purpose of report
     */
    @RequestMapping(path = "/missions/report", method = RequestMethod.POST)
    public void writeReport(@RequestParam("mission") int m,
                            @RequestParam("agent") int s,
                            @RequestParam("name") String name,
                            @RequestParam("desc") String desc,
                            @RequestParam("purp") String purp){
        Report r = new Report(
                missions.findById(m),
                agents.findById(s),
                manager.getCurrentAgent(),
                name,
                desc,
                purp);
        reportRepository.save(r);
    }
}