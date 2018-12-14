package bgs.controllers;

import bgs.info.RepairInfo;
import bgs.model.Agent;
import bgs.model.TransportRepair;
import bgs.model.WeaponRepair;
import bgs.repo.TransportRepairRepository;
import bgs.repo.WeaponRepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.stream.Stream;

@RestController
public class RepairController {
    @Autowired
    LoginManager manager;
    @Autowired
    WeaponRepairRepository wr;
    @Autowired
    TransportRepairRepository tr;


    /**
     * Returns active weapon repairs
     * @return Stream of repair info
     */
    @RequestMapping("/repairs/weapons")
    public Stream<RepairInfo> getWeaponRepairs(){
        return wr.findUnfinished().stream().map(RepairInfo::new);
    }


    /**
     * Returns active transport repairs
     * @return Stream of repair info
     */
    @RequestMapping("/repairs/transport")
    public Stream<RepairInfo> getTransportRepairs(){
        return tr.findUnfinished().stream().map(RepairInfo::new);
    }

    /**
     * Apply for weapon repair job
     * @param id Job ID
     * @return success
     */
    @RequestMapping(path = "/repairs/weapons/apply", method = RequestMethod.POST)
    public boolean applyWeapon(@RequestParam(name = "id") int id){
        WeaponRepair r = wr.findById(id);
        if(r.getResponsible()!=null)
            return false;
        Agent a = manager.getCurrentAgent();
        r.setResponsible(a);
        Timestamp ready = new Timestamp(System.currentTimeMillis() + 1000*60*60*24*10/a.getLevel());
        r.setReady(ready);
        wr.save(r);
        return true;
    }

    /**
     * Apply for transport repair job
     * @param id Job ID
     * @return success
     */
    @RequestMapping(path = "/repairs/transport/apply", method = RequestMethod.POST)
    public boolean applyTransport(@RequestParam(name = "id") int id){
        TransportRepair r = tr.findById(id);
        if(r.getResponsible()!=null)
            return false;
        Agent a = manager.getCurrentAgent();
        r.setResponsible(a);
        Timestamp ready = new Timestamp(System.currentTimeMillis() + 1000*60*60*24*10/a.getLevel());
        r.setReady(ready);
        tr.save(r);
        return true;
    }
}