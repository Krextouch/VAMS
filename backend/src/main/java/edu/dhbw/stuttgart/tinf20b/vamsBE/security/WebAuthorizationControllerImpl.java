package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.DisabledToken;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.DisabledTokenRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.RequestLogin;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.ResponseLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;

@Controller
public class WebAuthorizationControllerImpl implements WebAuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;
    private final UserAuthorizationService userAuthorizationService;

    @Autowired
    public WebAuthorizationControllerImpl(AuthenticationManager authenticationManager,
                                          JwtTokenProvider jwtTokenProvider,
                                          EmployeeRepository employeeRepository,
                                          UserAuthorizationService userAuthorizationService,
                                          DisabledTokenRepository disabledTokenRepository) {
        this.employeeRepository = employeeRepository;
        this.authenticationManager = authenticationManager;
        this.userAuthorizationService = userAuthorizationService;
    }

    @Override
    public ResponseLogin login(@RequestBody RequestLogin requestLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestLogin.getUsername(), requestLogin.getPassword())
        );
        return this.userAuthorizationService.getUserDetails(authentication);
    }

    @Override
    public void logout(@RequestHeader("Authorization") String bearer) {
        this.userAuthorizationService.disableToken(bearer);
    }

    @Override
    public ResponseLogin renew(@RequestHeader("Authorization") String bearer) {
        return userAuthorizationService.renewToken(bearer);
    }

}
