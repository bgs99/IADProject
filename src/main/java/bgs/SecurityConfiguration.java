package bgs;

import bgs.controllers.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("/**").hasAuthority("USER")
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
                .antMatchers("/repairs/*/apply").hasAuthority("REPAIR")
                .antMatchers("/*/accept").hasAuthority("CLERK")
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