package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.RequestLogin;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.ResponseLogin;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/v1")
public interface WebAuthorizationController {

    @PostMapping("/login")
    ResponseLogin login(@RequestBody RequestLogin requestLogin);

    @PostMapping("/logout")
    void logout(@RequestHeader("Authorization") String bearer);

    @GetMapping("/renew")
    ResponseLogin renew(@RequestHeader("Authorization") String bearer);
}
