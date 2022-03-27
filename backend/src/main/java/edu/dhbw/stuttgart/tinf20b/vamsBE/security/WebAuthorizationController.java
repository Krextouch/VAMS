package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.RequestLogin;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.ResponseLogin;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/v1")
public interface WebAuthorizationController {

    @PostMapping("/login")
    ResponseLogin login(@RequestBody RequestLogin requestLogin);
}
