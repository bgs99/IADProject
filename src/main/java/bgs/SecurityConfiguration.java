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
        return new CustomRoles();
    }

    class CustomRoles implements  RoleHierarchy{
        GrantedAuthority admin = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ADMIN";
            }
        };
        GrantedAuthority clerk = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "CLERK";
            }
        };

        GrantedAuthority user = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "USER";
            }
        };

        @Override
        public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
            List<GrantedAuthority> ret = new ArrayList<>();
            for (GrantedAuthority role :
                    authorities) {
                String name = role.getAuthority();
                if(name.equals("ADMIN")){
                    ret.add(admin);
                    ret.add(clerk);
                    ret.add(user);
                    continue;
                }
                if(name.equals("CLERK")){
                    ret.add(clerk);
                    ret.add(user);
                    continue;
                }
                if(name.equals("USER")){
                    ret.add(user);
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
                .antMatchers("/requests/process").hasAuthority("CLERK")
                .and()
                .formLogin()
                .and()
                .logout();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("0").password(passwordEncoder().encode("password")).authorities("ADMIN")
                .and()
                .withUser("18").password(passwordEncoder().encode("password")).authorities("CLERK");
    }
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}