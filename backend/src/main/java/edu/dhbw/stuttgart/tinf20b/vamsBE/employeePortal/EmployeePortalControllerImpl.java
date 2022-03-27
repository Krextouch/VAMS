package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.RequestLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class EmployeePortalControllerImpl implements EmployeePortalController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeePortalControllerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public String ping() {
        return "Hello Employee";
    }
}
