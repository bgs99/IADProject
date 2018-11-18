package bgs.controllers;

import bgs.model.*;
import bgs.repo.*;
import bgs.info.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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
        Team tm = new Team(manager.getCurrentAgent(), m, w, t, cover);
        teams.save(tm);
        w.decReady();
        weapons.save(w);
        if(t != null){
            t.decReady();
            transport.save(t);
        }
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
        //TODO send mail here
        return true;
    }
}