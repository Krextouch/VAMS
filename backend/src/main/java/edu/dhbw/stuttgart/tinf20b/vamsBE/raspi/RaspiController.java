package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi;

import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.SummonRequest;
import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.SummonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/raspi/api/v1")
public interface RaspiController {

    @PostMapping("/summon")
    SummonResponse summon(@RequestBody SummonRequest summonRequest);
}
