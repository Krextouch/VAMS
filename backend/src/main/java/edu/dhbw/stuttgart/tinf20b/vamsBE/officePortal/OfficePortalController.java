package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/office/api/v1")
public interface OfficePortalController {

    @GetMapping("/ping")
    String ping();

    @GetMapping("/testCar")
    void testCar();

}
