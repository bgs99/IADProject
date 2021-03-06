package bgs.controllers;

import bgs.info.AgentInfo;
import bgs.info.PlaceInfo;
import bgs.info.Registry;
import bgs.info.TargetInfo;
import bgs.model.Organisation;
import bgs.model.Person;
import bgs.model.Target;
import bgs.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@CrossOrigin
@RestController
public class PlaceController {
    @Autowired
    AgentRepository agents;
    @Autowired
    LoginManager manager;
    @Autowired
    TargetRepository targets;
    @Autowired
    PlaceRepository places;
    @Autowired
    PersonRepository people;
    @Autowired
    OrganisationRepository orgs;

    @RequestMapping("/bcrypt")
    public String getHash(@RequestParam(name = "pass", required=false, defaultValue="password") String pass){
        return new BCryptPasswordEncoder().encode(pass);
    }

    /**
     * Find place by name
     * @param q Search query
     * @return Stream of place info that corresponds to that name
     */
    @RequestMapping("/place/search")
    public Stream<PlaceInfo> findPlace(@RequestParam("query") String q){
        return places.findAllByName(q).stream().map(i -> new PlaceInfo(i, manager, agents, people, places, targets));
    }

    /**
     * Returns place info
     * @param id ID of a place
     * @return Place info
     */
    @RequestMapping({"/place"})
    public PlaceInfo getPlace(@RequestParam(name = "id", required=false, defaultValue="0") int id){

        return new PlaceInfo(places.findById(id), manager, agents, people, places, targets);
    }

    @Autowired
    MissionRepository missions;
    /**
     * Get locals from a place
     * @param id Place ID
     * @return List of person info
     */
    @RequestMapping("/place/locals")
    public Stream<Registry> getLocals(@RequestParam("id") int id){
        return people.findAllByLocationOrderByDangerDesc(places.findById(id)).stream()
                .map(q -> {
                    Target t = targets.findByPerson(q);
                    return new Registry(q, missions.countFinishedByPerson(q) == 0,
                            true, t == null ? null : t.getId());
                });
    }
    @RequestMapping("/place/org")
    public Stream<Registry> getOrgs(@RequestParam("id") int id){
        return orgs.findAllByLocationOrderByDangerDesc(places.findById(id)).stream()
                .map(q -> {
                    Target t = targets.findByOrganisation(q);
                    return new Registry(q, missions.countFinishedByOrganisation(q) == 0,
                            true, t == null ? null : t.getId());
                });
    }

    /**
     * Get agents from place
     * @param id Place ID
     * @return Stream of agent info
     */
    @RequestMapping("/place/locals/agents")
    public Stream<AgentInfo> getLocalAgents(@RequestParam("id") int id){
        int level = manager.getLevel();
        return agents.findAllByLocationAndLevel(places.findById(id), level).stream().map(AgentInfo::new);
    }
    @RequestMapping("/place/locals/targets")
    public Stream<TargetInfo> getLocalTargets(@RequestParam("id") int id){
        return targets.findAllPeopleByLocation(places.findById(id)).stream().map(TargetInfo::new);
    }
    @RequestMapping("/place/org/targets")
    public Stream<TargetInfo> getOrgTargets(@RequestParam("id") int id){
        return targets.findAllOrgsByLocation(places.findById(id)).stream().map(TargetInfo::new);
    }
}