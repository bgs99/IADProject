package bgs.controllers;

import bgs.info.MissionInfo;
import bgs.info.ReportInfo;
import bgs.info.TargetInfo;
import bgs.model.*;
import bgs.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
    EmailService mail;

    @RequestMapping("/targets")
    public Stream<TargetInfo> getTargets(@RequestParam(value = "page", defaultValue = "0") int page){
        return targets.findAllActive().stream().skip(page*10).limit(10).map(TargetInfo::new);
    }

    @RequestMapping("/missions")
    public Stream<MissionInfo> getMissions(@RequestParam(value = "page", defaultValue = "0") int page){
        return missions.findUnfinished().stream().skip(page * 10).limit(10).map(q -> new MissionInfo(q, teams));
    }

    @RequestMapping("/missions/create")
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

    @RequestMapping("/missions/apply")
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
    @RequestMapping("/missions/start")
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

    @RequestMapping("/missions/update")
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

    @RequestMapping("/missions/support/apply")
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

    @RequestMapping("/missions/support/process")
    public Stream<SupportRequest> getSupportRequests(@RequestParam(name = "page", defaultValue = "0", required = false) int page){
        return req.findAllBySeenIsFalse().stream().skip(page*10).limit(10);
    }

    @RequestMapping("/missions/support/send")
    public boolean sendSupport(@RequestParam("id") int id){
        SupportRequest r = req.findById(id);
        r.setSeen();
        req.save(r);
        return true;
    }

    @RequestMapping("/missions/reports")
    public Stream<ReportInfo> getReport(@RequestParam("id") int id){
        return reportRepository.findAllByMission(missions.findById(id)).stream().map(ReportInfo::new);
    }
}