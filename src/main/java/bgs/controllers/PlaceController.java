package bgs.controllers;

import bgs.info.AgentInfo;
import bgs.info.PlaceInfo;
import bgs.model.Person;
import bgs.repo.AgentRepository;
import bgs.repo.PersonRepository;
import bgs.repo.PlaceRepository;
import bgs.repo.TargetRepository;
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

    @RequestMapping("/bcrypt")
<<<<<<< HEAD
    public String gethash(){
        return new BCryptPasswordEncoder().encode("password");
    }

    @RequestMapping("/bdecrypt")
    public Boolean checkhash(@RequestParam("pass") String hah){
        return new BCryptPasswordEncoder().matches("password", hah);
=======
    public String getHash(@RequestParam(name = "pass", required=false, defaultValue="password") String pass){
        return new BCryptPasswordEncoder().encode(pass);
>>>>>>> a8e7f30f9397df49f7031f5a6998db0f9b3f32bb
    }

    @RequestMapping({"/place", "/"})
    public PlaceInfo getPlace(@RequestParam(name = "id", required=false, defaultValue="0") int id){

        return new PlaceInfo(places.findById(id), manager, agents, people, places, targets);
    }

    @RequestMapping("/place/locals")
    public List<Person> getLocals(@RequestParam("id") int id){
        return people.findAllByLocationOrderByDangerDesc(places.findById(id));
    }

    @RequestMapping("/place/locals/agents")
    public Stream<AgentInfo> getLocalAgents(@RequestParam("id") int id){
        int level = manager.getLevel();
        return agents.findAllByLocationAndLevel(places.findById(id), level).stream().map(AgentInfo::new);
    }

}