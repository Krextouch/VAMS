package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.AllEmployeeFilter;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.AllEmployeeResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.OpenReservationResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.ResetPasswordParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/office/api/v1")
public interface OfficePortalController {

    @GetMapping("/devtools")
    String devTools();

    @PostMapping("/createEmployee")
    void createEmployee(@RequestBody Employee employee);

    @DeleteMapping("/deleteEmployee/{employeeId}")
    void deleteEmployee(@PathVariable("employeeId") int employeeId);

    @PutMapping("/updateEmployee/{employeeId}")
    void updateEmployee(@PathVariable("employeeId") int employeeId, @RequestBody Employee employee);

    @PostMapping("/allEmployee")
    AllEmployeeResponse allEmployee(@RequestBody AllEmployeeFilter allEmployeeFilter);

    @PostMapping("/createVehicle")
    void createVehicle(@RequestBody Vehicle vehicle);

    @DeleteMapping("/deleteVehicle/{vin}")
    void deleteVehicle(@PathVariable("vin") String vin);

    @PutMapping("/updateVehicle/{vin}")
    void updateVehicle(@PathVariable("vin") String vin, @RequestBody Vehicle vehicle);

    @PutMapping("/verifyReservation/{reservationId}")
    void verifyReservation(@PathVariable("reservationId") int reservationId, @RequestBody String verifyIt);

    @GetMapping("/openReservationRequest")
    OpenReservationResponse openReservationRequest();

    @PostMapping("/resetPassword")
    String resetPassword(@RequestBody ResetPasswordParam resetPasswordParam);
}
