package bgs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
        return defaultWebSecurityExpressionHandler;
    }
    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ADMIN > MAP > USER and ADMIN > L5 > L4 > L3 > L2 > L1 > L0");
        return new CustomRoles();
    }

    class CustomRoles implements  RoleHierarchy{

        @Override
        public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
            List<GrantedAuthority> ret = new ArrayList<>();
            for (GrantedAuthority role :
                    authorities) {
                String name = role.getAuthority();
                if(name.equals("ADMIN")){
                    ret.add(new GrantedAuthority() {
                        @Override
                        public String getAuthority() {
                            return "MAP";
                        }
                    });
                }
                if(name.equals("MAP") || name.equals("ADMIN")){
                    ret.add(new GrantedAuthority() {
                        @Override
                        public String getAuthority() {
                            return "USER";
                        }
                    });
                    continue;
                }
                if(name.startsWith("L")){
                    final int i = Integer.parseInt(name.substring(1));
                    if(i > 0)
                        ret.add(new GrantedAuthority() {
                            @Override
                            public String getAuthority() {
                                return "L" + (i - 1);
                            }
                        });
                    continue;
                }
            }
            return ret;
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .expressionHandler(webExpressionHandler())
                .antMatchers("/**").hasAuthority("USER")
                .antMatchers("/place", "place/**").hasAuthority("MAP")
                .antMatchers("/agents/L5").hasAuthority("L5")
                .antMatchers("/agents/L4").hasAuthority("L4")
                .antMatchers("/agents/L3").hasAuthority("L3")
                .antMatchers("/agents/L2").hasAuthority("L2")
                .antMatchers("/agents/L1").hasAuthority("L1")
                .antMatchers("/agents/L0").hasAuthority("L0")
                .and()
                .formLogin()
                .and()
                .logout();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("login").password(passwordEncoder().encode("password")).authorities("ADMIN", "L5")
                .and()
                .withUser("agent").password(passwordEncoder().encode("password")).authorities("MAP", "L1");
    }
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}