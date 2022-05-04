package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi;

import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.SummonRequest;
import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.SummonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class RaspiControllerImpl implements RaspiController {

    private final RaspiService raspiService;

    @Autowired
    public RaspiControllerImpl(RaspiService raspiService) {
        this.raspiService = raspiService;
    }

    @Override
    public SummonResponse summon(@RequestBody SummonRequest summonRequest) {
        return this.raspiService.summon(summonRequest);
    }
}
