package bgs.controllers;

import bgs.model.Agent;
import bgs.model.Dept;
import bgs.repo.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginManager {

    @Autowired
    AgentRepository agents;

    public int getLevel(){
        return getCurrentAgent().getLevel();
    }
    public Agent getCurrentAgent(){
        String name = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return agents.findById(Integer.parseInt(name));
    }

    public Dept getJob(Agent a){
        Dept i;
        for(i = a.getDept(); i.getId() > 5; i = i.getParent());
        return i;
    }
}
