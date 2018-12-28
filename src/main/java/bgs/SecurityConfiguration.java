package bgs;

import bgs.controllers.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

    /*@Bean
    public CorsConfigurationSource corsConfigurationSource(){
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "DELETE", "POST"));
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/

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
                //.addFilter(new CorsFilter(corsConfigurationSource()))
                .csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .expressionHandler(webExpressionHandler())
                .antMatchers("/*").hasAuthority("USER")
                .antMatchers("/place", "place/**", "/agents/wage").hasAuthority("USER")
                .antMatchers("/requests/process").hasAuthority("CLERK")
                .antMatchers("/requests/process").hasAuthority("CLERK")
                .antMatchers("/agents/*").hasAuthority("CLERK")
                .antMatchers(
                        "/missions/create",
                        "/missions/start",
                        "/missions/support/process",
                        "/missions/support/send",
                        "/missions/reports").hasAuthority("CLERK")
                .antMatchers("/missions/apply").hasAuthority("FIELD")
                .antMatchers("/missions/update", "/missions/support/apply").hasAnyAuthority("FIELD", "CLERK")
                .antMatchers("/repairs/weapons/apply").hasAuthority("REPAIR")
                .and()
                .formLogin()
                .and()
                .logout();
    }

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}