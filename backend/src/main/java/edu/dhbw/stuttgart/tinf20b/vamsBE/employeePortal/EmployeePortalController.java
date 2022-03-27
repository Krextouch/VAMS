package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.RequestLogin;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/api/v1")
public interface EmployeePortalController {

    @GetMapping("/ping")
    String ping();
}
