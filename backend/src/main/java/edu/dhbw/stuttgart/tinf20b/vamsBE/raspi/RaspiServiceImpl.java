package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi;

import org.springframework.stereotype.Controller;

@Controller
public class RaspiServiceImpl implements RaspiService{

    @Override
    public String ping() {
        return "Hello World";
    }
}
