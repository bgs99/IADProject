package hello;

import model.Agent;
import model.People;
import model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import repo.*;

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

    @RequestMapping("/place")
    public String displayPlace(@RequestParam(name = "id", required=false, defaultValue="0") int id){
        String result = "";
        result += "Id: " +id +"<br>";
        Place current = places.findById(id);
        if(current.getLevel()>0) {
            Place par = current.getParent();
            result += String.format("<a href=\"/place?id=%d\">Parent: %s</a><br>", par.getId(), par.toString());
        }

        result += String.format("Population: <a href=\"/place/locals?id=%d\">%d</a><br>",id, current.getPopulation());
        List<Agent> ags = agents.findAllByLocation(current);
        result += String.format("Agents: <a href=\"/place/locals/agents?id=%d\">%d</a><br>",id, ags.size());
        result += String.format("Average danger: %f<br>Parts: <br>", people.getDanger(current));
        for(Place ps : places.findAllByParent(current)){
            result +=  String.format("<a href=\"/place?id=%d\">%s</a><br>",ps.getId(),ps.toString());
        }
        return result;
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