package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi;

import org.springframework.stereotype.Controller;

@Controller
public class RaspiControllerImpl implements RaspiController{

    @Override
    public String ping() {
        return "Hello World";
    }
}
