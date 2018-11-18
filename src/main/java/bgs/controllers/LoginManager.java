package bgs.controllers;

import bgs.model.Agent;
import bgs.repo.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
}
