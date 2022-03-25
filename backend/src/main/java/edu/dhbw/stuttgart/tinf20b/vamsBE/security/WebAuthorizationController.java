package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.RequestLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/v1")
public interface WebAuthorizationController {

    @PostMapping("/login")
    ResponseEntity login(@RequestBody RequestLogin requestLogin);

    @GetMapping("/login")
    ResponseEntity login();
}
