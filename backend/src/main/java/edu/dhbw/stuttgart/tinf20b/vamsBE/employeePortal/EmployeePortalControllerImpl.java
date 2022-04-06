package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.ReservationRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class EmployeePortalControllerImpl implements EmployeePortalController {

    private final EmployeeService employeeService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public EmployeePortalControllerImpl(EmployeeService employeeService, ReservationRepository reservationRepository) {
        this.employeeService = employeeService;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public String ping(@RequestHeader("Authorization") String authorization) {
        return "Hello " +
                this.employeeService.getEmployeeFromToken(authorization).getFirstName() + " " +
                this.employeeService.getEmployeeFromToken(authorization).getLastName();
    }

    @Override
    public void createReservation(Reservation reservation, String authorization) {
        this.employeeService.createReservation(reservation, authorization);
    }

    @Override
    public void updateReservation(Reservation reservation, String authorization) {
        this.employeeService.createReservation(reservation, authorization);
    }

    @Override
    public void deleteReservation(Reservation reservation, String authorization) {
        this.employeeService.deleteReservation(reservation, authorization);
    }

    @Override
    public AvailableVehicleResponse getAvailableVehicle(ReservationTimeframe reservationTimeframe) {
        return this.employeeService.getAvailableVehicle(reservationTimeframe);
    }

    @Override
    public SingleEmployeeReservationResponse getReservatedVehicle(String authorization) {
        return this.employeeService.getReservatedVehicle(authorization);
    }

    @Override
    public ReservationResponse allReservations(ReservationFilter reservationFilter, String authorization) {
        return this.employeeService.allReservations(reservationFilter, authorization);
    }
}
