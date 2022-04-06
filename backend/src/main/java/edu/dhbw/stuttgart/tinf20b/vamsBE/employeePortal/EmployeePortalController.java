package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/api/v1")
public interface EmployeePortalController {

    @GetMapping("/ping")
    String ping(@RequestHeader("Authorization") String authorization);

    @PostMapping("/createReservation")
    void createReservation(@RequestBody Reservation reservation, @RequestHeader("Authorization") String authorization);

    @PostMapping("/updateReservation")
    void updateReservation(@RequestBody Reservation reservation, @RequestHeader("Authorization") String authorization);

    @PostMapping("/deleteReservation")
    void deleteReservation(@RequestBody Reservation reservation, @RequestHeader("Authorization") String authorization);

    @PostMapping("/getAvailableVehicle")
    AvailableVehicleResponse getAvailableVehicle(@RequestBody ReservationTimeframe reservationTimeframe);

    @PostMapping("/getReservatedVehicle")
    SingleEmployeeReservationResponse getReservatedVehicle(@RequestHeader("Authorization") String authorization);

    @PostMapping("/allReservations")
    ReservationResponse allReservations(@RequestBody ReservationFilter reservationFilter, @RequestHeader("Authorization") String authorization);
}
