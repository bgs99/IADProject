package bgs.controllers;

import bgs.model.Agent;
import bgs.model.People;
import bgs.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bgs.repo.*;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

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
            cops = agents.countAgentsByLocation(place);
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
    public String addOne(@RequestParam("id") int id){
        String result = String.format("<a href=\"/place?id=%d\">Back</a><br>",id);

        return result + displayPeople(people.findAllByLocationOrderByDangerDesc(places.findById(id)));
    }

    public String displayPeople(List<People> l){
        String result = "";

        for (People man : l) {
            result +=  String.format("%s, sex : %s,  danger: %f, location: <a href=\"/place?id=%d\"> %s </a><br>",
                    man.toString(), man.isMale()? "male" : "female", man.getDanger(),
                    man.getLocation().getId(), man.getLocation().toString());
        }

        return result;
    }

    @RequestMapping("/place/locals/agents")
    public String findById(@RequestParam("id") int id){
        String result = "";

        return result;
    }
}