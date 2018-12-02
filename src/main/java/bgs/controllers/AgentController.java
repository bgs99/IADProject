package bgs.controllers;

import bgs.model.Agent;
import bgs.model.Dept;
import bgs.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgentController {
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
    @Autowired
    DeptRepository deptRepository;
    @Autowired
    EmailService mail;

    @RequestMapping("/agents/wage")
    public int getWage(@RequestParam("id") int id){
        return agents.findById(id).getPayment();
    }

    @RequestMapping("/agents/move")
    public void changeDept(@RequestParam("id") int id,
                           @RequestParam("dept") int dept){
        Agent a = agents.findById(id);
        Dept d = deptRepository.findById(dept);
        a.setDept(d);
        agents.save(a);
    }

    @RequestMapping("/agents/promote")
    public boolean promote(@RequestParam("id") int id){

        Agent a = agents.findById(id);
        if(a.getLevel() == 5)
            return false;
        a.setLevel(a.getLevel()+1);
        agents.save(a);


        mail.sendMail("You have been promoted", a,
                String.format("You have been promoted to level %d", a.getLevel()));

        return true;
    }


    @RequestMapping("/agents/demote")
    public boolean demote(@RequestParam("id") int id){
        Agent a = agents.findById(id);
        if(a.getLevel() == -1)
            return false;
        a.setLevel(a.getLevel()-1);
        agents.save(a);

        mail.sendMail("You have been demoted", a,
                String.format("You have been demoted to level %d", a.getLevel()));

        return true;
    }

    @RequestMapping("/agents/wage/set")
    public void setWage(@RequestParam("id") int id, @RequestParam("wage") int wage){
        Agent a = agents.findById(id);
        int oldwage = a.getPayment();

        if(wage == oldwage)
            return;
        a.setPayment(wage);
        agents.save(a);

        mail.sendMail("You wage have been changed", a,
                String.format("You wage have been %s from %d to %d RUB per month",
                        wage > oldwage ? "increased" : "decreased",
                        oldwage,
                        wage
                )
        );

    }
}