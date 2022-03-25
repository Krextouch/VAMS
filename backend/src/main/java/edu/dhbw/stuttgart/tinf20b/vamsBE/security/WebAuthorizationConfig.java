package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class WebAuthorizationConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebAuthorizationConfig(UserDetailsService userDetailsService,
                                  BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http

                .authorizeRequests()
                     //Public uris
                    .antMatchers( "/error", "/auth/api/v1/login", "/raspi/**").permitAll()
                    //Employee uris
                    .antMatchers("/employee/**").hasRole("EMPLOYEE")
                    //Office uris
                    .antMatchers("/office/**").hasRole("OFFICE")
                    .anyRequest().authenticated()
                    .and()
                //if user isn't authorized
                .formLogin()
                    .loginPage("/unauthorized")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();

        //filter video 1:04:44 https://www.youtube.com/watch?v=mYKf4pufQWA&t=3897s
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
