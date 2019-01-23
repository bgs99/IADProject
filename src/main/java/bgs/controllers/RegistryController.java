package bgs.controllers;

import bgs.info.Registry;
import bgs.model.Organisation;
import bgs.model.Person;
import bgs.model.RegistryEntry;
import bgs.model.Target;
import bgs.repo.MissionRepository;
import bgs.repo.OrganisationRepository;
import bgs.repo.PersonRepository;
import bgs.repo.TargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    /**
     * Returns 10 active persons from registry
     * @param page Page index
     * @return List of registry info
     */
    @RequestMapping("/registry/people")
    public Stream<Registry> getPeople(@RequestParam(value = "page", defaultValue = "0") int page){
        return people.findAll(PageRequest.of(page, 10)).stream()
                .map(q -> {
                    Target t = targets.findByPerson(q);
                    return new Registry(q, missions.countFinishedByPerson(q) == 0,
                            true, t == null ? null : t.getId());
                });
    }

    /**
     * Returns 10 active organisations from registry
     * @param page Page index
     * @return List of registry info
     */
    @RequestMapping("/registry/org")
    public Stream<Registry> getOrganisations(@RequestParam(value = "page", defaultValue = "0") int page){

        return organisations.findAll().stream().skip(page*10).limit(10)
                .map(q -> {
                    Target t = targets.findByOrganisation(q);
                    return new Registry(q, missions.countFinishedByOrganisation(q) == 0,
                            true, t == null ? null : t.getId());
                });
    }

    /**
     * Mark person as target
     * @param id Person passport
     * @return success
     */
    @RequestMapping(path = "/registry/people/target", method = RequestMethod.POST)
    public Integer setTargetPerson(@RequestParam(value = "id") int id){
        Person p = people.findById(id);
        if(p == null)
            return null;
        Target t = new Target(p);
        targets.save(t);
        return t.getId();
    }

    /**
     * Mark organisation as target
     * @param id Organisation ID
     * @return success
     */
    @RequestMapping(path = "/registry/org/target", method = RequestMethod.POST)
    public Integer setTargetOrganisation(@RequestParam(value = "id") int id){
        Organisation p = organisations.findById(id);
        if(p == null)
            return null;
        Target t = new Target(p);
        targets.save(t);
        return t.getId();
    }
}