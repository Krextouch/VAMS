package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/office/api/v1")
public interface OfficePortalController {

    @GetMapping("/ping")
    String ping();

    @GetMapping("/testCar")
    void testCar();

    @PostMapping("/createEmployee")
    void createEmployee(@RequestBody Employee employee);

    @PostMapping("/deleteEmployee")
    void deleteEmployee(@RequestBody Employee employee);
}
