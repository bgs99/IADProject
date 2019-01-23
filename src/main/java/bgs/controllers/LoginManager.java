package bgs.controllers;

import bgs.model.Agent;
import bgs.model.Dept;
import bgs.model.Mission;
import bgs.model.Team;
import bgs.repo.AgentRepository;
import bgs.repo.MissionRepository;
import bgs.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class LoginManager {

    @Autowired
    AgentRepository agents;
    @Autowired
    TeamRepository missions;
    @Autowired
    MissionRepository mr;

    public Integer getCurrentMission() {
        Agent c = getCurrentAgent();
        if(getJob(c).getId() >=4){

        }
        Optional<Team> f = missions.findActiveByAgent(c);
        return f.map(q -> q.getMission().getId())
                .orElse(mr.findActiveByResponsible(c).map(q -> q.getId())
                        .orElse(null));
    }
    public int getLevel(){
        return getCurrentAgent().getLevel();
    }
    public Stream<String> getRights() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAuthorities().stream().map(q -> ((GrantedAuthority) q).getAuthority());
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
