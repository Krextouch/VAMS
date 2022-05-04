package edu.dhbw.stuttgart.tinf20b.vamsBE.core.redirect;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectControllerImpl implements RedirectController {

    @Override
    public RedirectView redirectFrontEnd() {
        return new RedirectView("https://vams.server-welt.com");
    }
}
