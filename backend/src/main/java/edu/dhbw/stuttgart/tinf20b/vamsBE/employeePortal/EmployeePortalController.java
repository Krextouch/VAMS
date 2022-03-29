package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/api/v1")
public interface EmployeePortalController {

    @GetMapping("/ping")
    String ping();

    @PostMapping("/createReservation")
    void createReservation(@RequestBody Reservation reservation);

    @PostMapping("/updateReservation")
    void updateReservation(@RequestBody Reservation reservation);
}
