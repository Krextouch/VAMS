package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.VehicleService;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.OpenReservationResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.VerifyReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OfficePortalControllerImpl implements OfficePortalController{

    private final VehicleService vehicleService;
    private final OfficeService officeService;

    @Autowired
    public OfficePortalControllerImpl(VehicleService vehicleService, OfficeService officeService){
       this.vehicleService = vehicleService;
        this.officeService = officeService;
    }

    @Override
    public String ping() {
        return "Hello Office";
    }

    //delete prod only for test
    @Override
    public void testCar(){
        this.vehicleService.setVehicleTest();
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
