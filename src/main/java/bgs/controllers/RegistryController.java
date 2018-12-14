package bgs.controllers;

import bgs.model.Organisation;
import bgs.model.Person;
import bgs.model.RegistryEntry;
import bgs.model.Target;
import bgs.repo.MissionRepository;
import bgs.repo.OrganisationRepository;
import bgs.repo.PersonRepository;
import bgs.repo.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RegistryController {
    @Autowired
    LoginManager manager;
    @Autowired
    PersonRepository people;
    @Autowired
    MissionRepository missions;
    @Autowired
    OrganisationRepository organisations;
    @Autowired
    TargetRepository targets;

    class Registry{
        public String name;
        public int id;
        public Pair<Integer, String> location;
        public double danger;
        public boolean status;
        public Registry(RegistryEntry r, boolean alive){
            name = r.getName();
            id = r.getId();
            location = Pair.of(r.getLocation().getId(), r.getLocation().getName());
            danger = r.getDanger();
            status = alive;
        }
    }

    /**
     * Returns 10 active persons from registry
     * @param page Page index
     * @return List of registry info
     */
    @RequestMapping("/registry/people")
    public List<Registry> getPeople(@RequestParam(value = "page", defaultValue = "0") int page){
        List<Registry> ret = new ArrayList<>();
        int skip = page * 10;
        int take = 10;
        for (Person p : people.findAll()){
            if(skip > 0){
                skip--;
                continue;
            }
            if(take < 0)
                break;
            take--;
            ret.add(new Registry(p, missions.countFinishedByPerson(p) == 0));
        }
        return ret;
    }

    /**
     * Returns 10 active organisations from registry
     * @param page Page index
     * @return List of registry info
     */
    @RequestMapping("/registry/org")
    public List<Registry> getOrganisations(@RequestParam(value = "page", defaultValue = "0") int page){
        List<Registry> ret = new ArrayList<>();
        int skip = page * 10;
        int take = 10;
        for (Organisation o : organisations.findAll()){
            if(skip > 0){
                skip--;
                continue;
            }
            if(take < 0)
                break;
            take--;
            ret.add(new Registry(o, missions.countFinishedByOrganisation(o)==0));
        }
        return ret;
    }

    /**
     * Mark person as target
     * @param id Person passport
     * @return success
     */
    @RequestMapping(path = "/registry/people/target", method = RequestMethod.POST)
    public boolean setTargetPerson(@RequestParam(value = "id") int id){
        Person p = people.findById(id);
        if(p == null)
            return false;
        Target t = new Target(p);
        targets.save(t);
        return true;
    }

    /**
     * Mark organisation as target
     * @param id Organisation ID
     * @return success
     */
    @RequestMapping(path = "/registry/org/target", method = RequestMethod.POST)
    public boolean setTargetOrganisation(@RequestParam(value = "id") int id){
        Organisation p = organisations.findById(id);
        if(p == null)
            return false;
        Target t = new Target(p);
        targets.save(t);
        return true;
    }
}