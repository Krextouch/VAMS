package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
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
}
