package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee/api/v1")
public interface EmployeePortalController {

    @PostMapping("/createReservation")
    void createReservation(@RequestBody Reservation reservation, @RequestHeader("Authorization") String authorization);

    @PutMapping("/updateReservation/{reservationId}")
    void updateReservation(@PathVariable("reservationId") int reservationId, @RequestBody Reservation reservation, @RequestHeader("Authorization") String authorization);

    @DeleteMapping("/deleteReservation/{reservationId}")
    void deleteReservation(@PathVariable("reservationId") int reservationId, @RequestHeader("Authorization") String authorization);

    @PostMapping("/getAvailableVehicle")
    AvailableVehicleResponse getAvailableVehicle(@RequestParam("start") LocalDateTime startTime, @RequestParam("end") LocalDateTime endTime);

    @GetMapping("/getReservatedVehicle")
    SingleEmployeeReservationResponse getReservedVehicle(@RequestHeader("Authorization") String authorization);

    @PostMapping("/allReservations")
    ReservationResponse allReservations(@RequestBody ReservationFilter reservationFilter, @RequestHeader("Authorization") String authorization);

    @PostMapping("/passwordReset")
    String passwordReset(@RequestBody PasswordResetParam passwordResetParam);
}
