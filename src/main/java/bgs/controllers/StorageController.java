package bgs.controllers;

import bgs.model.Transport;
import bgs.model.Weapon;
import bgs.repo.AgentRepository;
import bgs.repo.TargetRepository;
import bgs.repo.TransportRepository;
import bgs.repo.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {
    @Autowired
    AgentRepository agents;
    @Autowired
    LoginManager manager;
    @Autowired
    TargetRepository targets;
    @Autowired
    TransportRepository transport;
    @Autowired
    WeaponRepository weapons;

    @RequestMapping("/weapons/order")
    public void orderWeapons(@RequestParam("id") int id,
                       @RequestParam(name = "amount", defaultValue = "1", required = false) int amount){
        Weapon w = weapons.findById(id);
        w.setOrdered(w.getOrdered()+amount);
        weapons.save(w);
    }

    @RequestMapping("/transport/order")
    public void orderTransport(@RequestParam("id") int id,
                           @RequestParam(name = "amount", defaultValue = "1", required = false) int amount){
        Transport w = transport.findById(id);
        w.setOrdered(w.getOrdered()+amount);
        transport.save(w);
    }

    @RequestMapping("/weapons/accept")
    public void acceptWeapons(@RequestParam("id") int id){
        Weapon w = weapons.findById(id);
        w.incReady(w.getOrdered());
        w.setOrdered(0);
        weapons.save(w);
    }


    @RequestMapping("/transport/accept")
    public void acceptTransport(@RequestParam("id") int id){
        Transport w = transport.findById(id);
        w.incReady(w.getOrdered());
        w.setOrdered(0);
        transport.save(w);
    }
}
