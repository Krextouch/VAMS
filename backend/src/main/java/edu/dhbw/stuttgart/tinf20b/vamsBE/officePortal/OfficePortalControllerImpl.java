package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.AllEmployeeResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.OpenReservationResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.VerifyReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OfficePortalControllerImpl implements OfficePortalController {

    private final OfficeService officeService;

    @Autowired
    public OfficePortalControllerImpl(OfficeService officeService) {
        this.officeService = officeService;
    }

    @Override
    public String ping() {
        return "Hello Office";
    }

    @Override
    public void createEmployee(Employee employee) {
        this.officeService.createEmployee(employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        this.officeService.deleteEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        this.officeService.createEmployee(employee);
    }

    @Override
    public AllEmployeeResponse allEmployee() {
        return this.officeService.allEmployee();
    }

    @Override
    public void createVehicle(Vehicle vehicle) {
        this.officeService.createVehicle(vehicle);
    }

    @Override
    public void deleteVehicle(Vehicle vehicle) {
        this.officeService.deleteVehicle(vehicle);
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        this.officeService.createVehicle(vehicle);
    }

    @Override
    public void verifyReservation(VerifyReservationRequest verifyReservationRequest) {
        this.officeService.verifyReservation(verifyReservationRequest);
    }

    @Override
    public OpenReservationResponse openReservationRequest() {
        return this.officeService.openReservationRequest();
    }
}
