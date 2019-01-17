package bgs.controllers;

import bgs.info.TransportInfo;
import bgs.info.WeaponInfo;
import bgs.model.Transport;
import bgs.model.Weapon;
import bgs.repo.AgentRepository;
import bgs.repo.TargetRepository;
import bgs.repo.TransportRepository;
import bgs.repo.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

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

    /**
     * Order weapons
     * @param id Weapon ID
     * @param amount Weapon amount
     */
    @RequestMapping(path = "/weapons/order", method = RequestMethod.POST)
    public void orderWeapons(@RequestParam("id") int id,
                       @RequestParam(name = "amount", defaultValue = "1", required = false) int amount){
        Weapon w = weapons.findById(id);
        w.setOrdered(w.getOrdered()+amount);
        weapons.save(w);
    }

    @RequestMapping(path = "/weapons")
    public Stream<WeaponInfo> listWeapons(@RequestParam(value = "id", defaultValue = "-1") int id){
        if(id >= 0)
            return Stream.of(weapons.findById(id)).map(WeaponInfo::new);
        return weapons.findAll().stream().map(WeaponInfo::new);
    }
    @RequestMapping(path = "/transport")
    public Stream<TransportInfo> listTransport(@RequestParam(value = "id", defaultValue = "-1") int id){
        if(id >= 0)
            return Stream.of(transport.findById(id)).map(TransportInfo::new);
        return transport.findAll().stream().map(TransportInfo::new);
    }
    /**
     * Order transport
     * @param id Transport ID
     * @param amount Transport amount
     */
    @RequestMapping(path = "/transport/order", method = RequestMethod.POST)
    public void orderTransport(@RequestParam("id") int id,
                           @RequestParam(name = "amount", defaultValue = "1", required = false) int amount){
        Transport w = transport.findById(id);
        w.setOrdered(w.getOrdered()+amount);
        transport.save(w);
    }

    /**
     * Accept weapon order
     * @param id Order ID
     */
    @RequestMapping(path = "/weapons/accept", method = RequestMethod.POST)
    public void acceptWeapons(@RequestParam("id") int id){
        Weapon w = weapons.findById(id);
        w.incReady(w.getOrdered());
        w.setOrdered(0);
        weapons.save(w);
    }

    /**
     * Accept transport order
     * @param id Transport ID
     */
    @RequestMapping(path = "/transport/accept", method = RequestMethod.POST)
    public void acceptTransport(@RequestParam("id") int id){
        Transport w = transport.findById(id);
        w.incReady(w.getOrdered());
        w.setOrdered(0);
        transport.save(w);
    }
}
