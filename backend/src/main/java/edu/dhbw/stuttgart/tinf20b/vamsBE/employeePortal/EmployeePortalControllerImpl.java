package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.ReservationRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Locale;

@Controller
public class EmployeePortalControllerImpl implements EmployeePortalController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeePortalControllerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void createReservation(Reservation reservation, String authorization) {
        this.employeeService.createReservation(reservation, authorization);
    }

    @Override
    public void updateReservation(@PathVariable("reservationId") int reservationId, @RequestBody Reservation reservation, String authorization) {
        reservation.setId(reservationId);
        this.employeeService.updateReservation(reservation, authorization);
    }

    @Override
    public void deleteReservation(@PathVariable("reservationId") int reservationId, String authorization) {
        this.employeeService.deleteReservation(reservationId, authorization);
    }

    @Override
    public AvailableVehicleResponse getAvailableVehicle(@RequestParam("start") LocalDateTime startTime, @RequestParam("end") LocalDateTime endTime) {
        return this.employeeService.getAvailableVehicle(startTime, endTime);
    }

    @Override
    public SingleEmployeeReservationResponse getReservedVehicle(String authorization) {
        return this.employeeService.getReservatedVehicle(authorization);
    }

    @Override
    public ReservationResponse allReservations(ReservationFilter reservationFilter, String authorization) {
        return this.employeeService.allReservations(reservationFilter, authorization);
    }

    @Override
    public String passwordReset(PasswordResetParam passwordResetParam) {
        return employeeService.passwordReset(passwordResetParam);
    }
}
