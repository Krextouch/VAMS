package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.RequestLogin;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.ResponseLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebAuthorizationControllerImpl implements  WebAuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmployeeRepository employeeRepository;
    private final UserAuthorizationService userAuthorizationService;

    @Autowired
    public WebAuthorizationControllerImpl(AuthenticationManager authenticationManager,
                                          JwtTokenProvider jwtTokenProvider,
                                          EmployeeRepository employeeRepository,
                                          UserAuthorizationService userAuthorizationService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.employeeRepository = employeeRepository;
        this.userAuthorizationService = userAuthorizationService;
    }

    @Override
    public ResponseLogin login(@RequestBody RequestLogin requestLogin) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestLogin.getUsername(),requestLogin.getPassword())
        );
        return this.userAuthorizationService.getUserDetails(authentication);
    }

}
