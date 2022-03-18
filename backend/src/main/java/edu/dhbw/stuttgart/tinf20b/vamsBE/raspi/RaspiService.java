package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/raspi/api/v1")
public interface RaspiService {

    @GetMapping("/ping")
    String ping();
}
