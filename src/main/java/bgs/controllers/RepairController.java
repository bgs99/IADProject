package bgs.controllers;

import bgs.info.RepairInfo;
import bgs.model.*;
import bgs.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class RepairController {
    @Autowired
    LoginManager manager;
    @Autowired
    WeaponRepairRepository wr;
    @Autowired
    TransportRepairRepository tr;



    @RequestMapping("/repairs/weapons")
    public Stream<RepairInfo> getWeaponRepairs(){
        return wr.findUnfinished().stream().map(RepairInfo::new);
    }

    @RequestMapping("/repairs/transport")
    public Stream<RepairInfo> getTransportRepairs(){
        return tr.findUnfinished().stream().map(RepairInfo::new);
    }

    @RequestMapping("/repairs/weapons/apply")
    public boolean applyWeapon(@RequestParam(name = "id") int id){
        WeaponRepair r = wr.findById(id);
        if(r.getResponisble()!=null)
            return false;
        Agent a = manager.getCurrentAgent();
        r.setResponisble(a);
        Timestamp ready = new Timestamp(System.currentTimeMillis() + 1000*60*60*24*10/a.getLevel());
        r.setReady(ready);
        wr.save(r);
        return true;
    }
    @RequestMapping("/repairs/transport/apply")
    public boolean applyTransport(@RequestParam(name = "id") int id){
        TransportRepair r = tr.findById(id);
        if(r.getResponisble()!=null)
            return false;
        Agent a = manager.getCurrentAgent();
        r.setResponisble(a);
        Timestamp ready = new Timestamp(System.currentTimeMillis() + 1000*60*60*24*10/a.getLevel());
        r.setReady(ready);
        tr.save(r);
        return true;
    }
}