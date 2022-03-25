package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.RequestLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebAuthorizationControllerImpl implements  WebAuthorizationController{

    private final WebAuthorizationService webAuthorizationService;

    @Autowired
    public WebAuthorizationControllerImpl(WebAuthorizationService webAuthorizationService){
        this.webAuthorizationService = webAuthorizationService;
    }

    //PostMethod
    @Override
    public ResponseEntity login(@RequestBody RequestLogin requestLogin) {
        return new ResponseEntity<>("Hi", HttpStatus.OK);
        //this.webAuthorizationService.login(requestLogin.getUsername(), requestLogin.getPassword());
    }

    //GetMethod - Login can only made via Post Command
    @Override
    public ResponseEntity login() {
        return new ResponseEntity<>("405: Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
