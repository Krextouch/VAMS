package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.AvailableVehicleResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.ReservationTimeframe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

    @Override
    public void createReservation(Reservation reservation) {
        this.employeeService.createReservation(reservation);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        this.employeeService.createReservation(reservation);
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        this.employeeService.deleteReservation(reservation);
    }

    @Override
    public AvailableVehicleResponse getAvailableVehicle(ReservationTimeframe reservationTimeframe) {
        return this.employeeService.getAvailableVehicle(reservationTimeframe);
    }
}
