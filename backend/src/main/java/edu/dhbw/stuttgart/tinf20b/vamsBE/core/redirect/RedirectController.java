package edu.dhbw.stuttgart.tinf20b.vamsBE.core.redirect;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public interface RedirectController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    RedirectView redirectFrontEnd();

}
