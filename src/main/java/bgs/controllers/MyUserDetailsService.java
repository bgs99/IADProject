package bgs.controllers;

import bgs.MyUserPrincipal;
import bgs.model.Agent;
import bgs.repo.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AgentRepository userRepository;
    @Autowired
    private LoginManager loginManager;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Agent user = null;
        try {
            user = userRepository.findById(Integer.parseInt(username));
        }catch (Exception e){
            throw new UsernameNotFoundException(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user, loginManager.getJob(user));
    }
}