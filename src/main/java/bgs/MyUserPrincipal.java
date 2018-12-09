package bgs;

import bgs.model.Agent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserPrincipal implements UserDetails {
    private Agent user;
    private int role;

    public MyUserPrincipal(Agent user, int role) {
        this.user = user;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> ret = new ArrayList<>();
        ret.add(() -> "USER");

        switch(role){
            case 1:
                ret.add(() -> "REPAIR");
                break;
            case 3:
                ret.add(()->"FIELD");
                break;
            case 4:
                ret.add(()->"WEAPONS");
                break;
            case 5:
                ret.add(()->"TRANSPORT");
                break;
            case 0:
                ret.add(()->"ADMIN");
            case 2:
                ret.add(()->"CLERK");
                break;
            default:
                return null;
        }
        return ret;
    }

    @Override
    public String getPassword() {
        return user.getPass();
    }

    @Override
    public String getUsername() {
        return String.valueOf(user.getId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getLevel() > 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
