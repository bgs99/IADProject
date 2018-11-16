package bgs.controllers;

import bgs.model.Agent;
import bgs.model.Dept;
import bgs.model.People;
import bgs.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bgs.repo.*;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class DataController {
    @Autowired
    TargetRepository targets;
    @Autowired
    PlaceRepository places;
    @Autowired
    PeopleRepository people;
    @Autowired
    AgentRepository agents;
    @Autowired
    DeptRepository depts;

    public class PlaceInfo{
        public String name;
        public int id;
        public Integer parentId;
        public String parentName;
        public List<Pair<Integer, String>> units = new ArrayList<>();
        public double danger;
        public long population;
        public int cops;
        public PlaceInfo(Place place){

            name= place.getName();
            id = place.getId();

            Place parent = place.getLevel() > 0 ? place.getParent() : null;
            parentId = parent == null ? null : parent.getId();
            parentName = parent == null ? null : parent.getName();

            population = place.getPopulation();
            int level = 5;//getLevel();
            cops = agents.countAgentsByLocationAndLevel(place, level);
            danger = people.getDanger(place);
            for(Place ps : places.findAllByParent(place)){
                units.add(Pair.of(ps.getId(), ps.getName()));
            }
        }
    }

    @RequestMapping("/place")
    public PlaceInfo getPlace(@RequestParam(name = "id", required=false, defaultValue="0") int id){
        return new PlaceInfo(places.findById(id));
    }

    @RequestMapping("/place/locals")
    public List<People> getLocals(@RequestParam("id") int id){
        return people.findAllByLocationOrderByDangerDesc(places.findById(id));
    }

    private int getLevel(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(q -> ((GrantedAuthority) q).getAuthority())
                .filter(s -> s.startsWith("L"))
                .map(s -> Integer.parseInt(s.substring(1)))
                .sorted()
                .findFirst().get();
    }

    class AgentInfo{
        public String name;
        public int id;
        public String deptName;
        public int deptId;
        public Integer salary;
        public int level;
        public boolean free;
        public double trust;
        public String imgPath = null;//TODO add to DB
        public AgentInfo(Agent agent){
            People p = agent.getPassport();
            id = agent.getId();
            name = p.toString();
            Dept d = agent.getDept();
            deptName = d.toString();
            deptId = d.getId();
            salary = agent.getPayment();
            level = agent.getLevel();
            free = true;//TODO find that
            trust = 50;//TODO THAT TOO

        }
    }

    @RequestMapping("/place/locals/agents")
    public List<AgentInfo> getLocalAgents(@RequestParam("id") int id){
        int level = getLevel();
        return agents.findAllByLocationAndLevel(places.findById(id), level).stream().map(q -> new AgentInfo(q)).collect(Collectors.toList());
    }
}