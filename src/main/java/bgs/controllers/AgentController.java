package bgs.controllers;

import bgs.info.AgentInfo;
import bgs.info.LoginInfo;
import bgs.model.Agent;
import bgs.model.Dept;
import bgs.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Stream;

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

    private static RequestMethod rm = RequestMethod.GET;

    @RequestMapping("/dept")
    public Stream<String> showDept(){
        return manager.getRights();
    }

    @Autowired
    MyUserDetailsService muds;

    @Autowired
    AuthenticationProvider ap;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login() {
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public LoginInfo login(@RequestParam("login") int id, @RequestParam("password") String pass) throws Exception {
        UserDetails userDetails = muds.loadUserByUsername(""+id);
        if(! new BCryptPasswordEncoder().matches(pass, userDetails.getPassword())){
            return null;
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, pass, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        Agent c = manager.getCurrentAgent();
        return new LoginInfo(id, manager.getJob(c), c, manager.getRights(), manager.getCurrentMission());
    }

    /**
     * Returns current wage of the agent
     * @param id ID of an agent
     * @return amount in RUB
     */
    @RequestMapping("/agents/wage")
    public int getWage(@RequestParam("id") int id){
        return agents.findById(id).getPayment();
    }

    @Transactional
    @RequestMapping("/agents")
    public Stream<AgentInfo> listAgents(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "id", defaultValue = "-1") int id){
        if(id >= 0)
            return Stream.of(new AgentInfo(agents.findById(id)));
        int level = manager.getLevel();

        return agents.findAllByLevelLessThanEqual(level).stream()
                .filter(q -> q.getDept().getId() == manager.getCurrentAgent().getDept().getId())
                .skip(page*10).limit(10).map(AgentInfo::new);
    }

    /**
     * Moves agent to another department
     * @param id ID of an agent
     * @param dept ID of a department
     */
    @RequestMapping(value = "/agents/move", method = RequestMethod.POST)
    public void changeDept(@RequestParam("id") int id,
                           @RequestParam("dept") int dept){
        Agent a = agents.findById(id);
        Dept old = a.getDept();
        Dept d = deptRepository.findById(dept);
        a.setDept(d);
        agents.save(a);

        mail.sendMail("You have been moved", a,
                String.format("You have been moved from %s to %s", old.toString(), d.toString()));
    }

    /**
     * Promotes agent to a new level
     * @param id ID of an agent
     * @return success
     */
    @RequestMapping(path = "/agents/promote", method = RequestMethod.GET)
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

    /**
     * Demotes agent to a lower level
     * @param id ID of an agent
     * @return success
     */
    @RequestMapping(path = "/agents/demote", method = RequestMethod.POST)
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

    /**
     * Changes agent's wage
     * @param id ID of an agent
     * @param wage New wage in RUB
     */
    @RequestMapping(path = "/agents/wage/set", method = RequestMethod.POST)
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