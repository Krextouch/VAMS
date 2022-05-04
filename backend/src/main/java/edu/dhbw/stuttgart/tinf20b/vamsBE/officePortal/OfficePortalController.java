package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.AllEmployeeFilter;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.AllEmployeeResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.OpenReservationResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.VerifyReservationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/office/api/v1")
public interface OfficePortalController {

    @PostMapping("/createEmployee")
    void createEmployee(@RequestBody Employee employee);

    @DeleteMapping("/deleteEmployee/{employeeId}")
    void deleteEmployee(@PathVariable("employeeId") int employeeId);

    @PutMapping("/updateEmployee")
    void updateEmployee(@RequestBody Employee employee);

    @GetMapping("/allEmployee")
    AllEmployeeResponse allEmployee(@RequestBody AllEmployeeFilter allEmployeeFilter);

    @PostMapping("/createVehicle")
    void createVehicle(@RequestBody Vehicle vehicle);

    @DeleteMapping("/deleteVehicle/{vin}")
    void deleteVehicle(@PathVariable("vin") String vin);

    @PutMapping("/updateVehicle")
    void updateVehicle(@RequestBody Vehicle vehicle);

    @PutMapping("/verifyReservation")
    void verifyReservation(@RequestBody VerifyReservationRequest verifyReservationRequest);

    @GetMapping("/openReservationRequest")
    OpenReservationResponse openReservationRequest();
}
