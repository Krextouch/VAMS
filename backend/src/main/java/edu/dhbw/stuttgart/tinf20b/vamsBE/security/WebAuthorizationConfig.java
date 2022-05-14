package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.DisabledTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class WebAuthorizationConfig extends WebSecurityConfigurerAdapter {

    private final WebAuthEntryPoint webAuthEntryPoint;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserAuthorizationService webAuthorizationService;
    private final DisabledTokenRepository disabledTokenRepository;

    @Autowired
    public WebAuthorizationConfig(JwtTokenProvider jwtTokenProvider,
                                  UserAuthorizationService webAuthorizationService,
                                  WebAuthEntryPoint webAuthEntryPoint,
                                  DisabledTokenRepository disabledTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.webAuthorizationService = webAuthorizationService;
        this.webAuthEntryPoint = webAuthEntryPoint;
        this.disabledTokenRepository = disabledTokenRepository;
    }

    //Configure endpoints settings
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        //unauthorized forwarding
        http.exceptionHandling().authenticationEntryPoint(webAuthEntryPoint);
        http

                .authorizeRequests()
                //Public uris
                .antMatchers("/error", "/unauthorized", "/auth/api/v1/login", "/raspi/**", "/").permitAll()
                //Employee uris
                .antMatchers("/employee/**", "/auth/api/v1/logout", "/auth/api/v1/renew").hasRole("EMPLOYEE")
                //Office uris
                .antMatchers("/office/**").hasRole("OFFICE")
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebAuthenticationFilter authenticationFilter() {
        return new WebAuthenticationFilter(this.jwtTokenProvider, this.webAuthorizationService, this.disabledTokenRepository);
    }
}
