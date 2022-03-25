package edu.dhbw.stuttgart.tinf20b.vamsBE.core.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorPage implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity error(){
        return new ResponseEntity<>("404 Page not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/unauthorized")
    public ResponseEntity unauthorized(){
        return new ResponseEntity<>("401 Unauthorized", HttpStatus.UNAUTHORIZED);
    }

}
