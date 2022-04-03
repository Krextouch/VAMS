package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.OpenReservationResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.VerifyReservationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/office/api/v1")
public interface OfficePortalController {

    @GetMapping("/ping")
    String ping();

    @PostMapping("/createEmployee")
    void createEmployee(@RequestBody Employee employee);

    @PostMapping("/deleteEmployee")
    void deleteEmployee(@RequestBody Employee employee);

    @PostMapping("/updateEmployee")
    void updateEmployee(@RequestBody Employee employee);

    @PostMapping("/createVehicle")
    void createVehicle(@RequestBody Vehicle vehicle);

    @PostMapping("/deleteVehicle")
    void deleteVehicle(@RequestBody Vehicle vehicle);

    @PostMapping("/updateVehicle")
    void updateVehicle(@RequestBody Vehicle vehicle);

    @PostMapping("/verifyReservation")
    void verifyReservation(@RequestBody VerifyReservationRequest verifyReservationRequest);

    @PostMapping("/openReservationRequest")
    OpenReservationResponse openReservationRequest();
}
