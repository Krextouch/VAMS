package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.AllEmployeeFilter;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.AllEmployeeResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.OpenReservationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OfficePortalControllerImpl implements OfficePortalController {

    private final OfficeService officeService;
    private final BuildProperties buildProperties;

    @Autowired
    public OfficePortalControllerImpl(OfficeService officeService, BuildProperties buildProperties) {
        this.officeService = officeService;
        this.buildProperties = buildProperties;
    }

    @Override
    public String devTools() {
        return "Version " + buildProperties.getVersion() + " | Build " + buildProperties.getTime();
    }

    @Override
    public void createEmployee(@RequestBody Employee employee) {
        this.officeService.createEmployee(employee);
    }

    @Override
    public void deleteEmployee(@PathVariable("employeeId") int employeeId) {
        this.officeService.deleteEmployee(employeeId);
    }

    @Override
    public void updateEmployee(@PathVariable("employeeId") int employeeId, Employee employee) {
        employee.setEmployeeId(employeeId);
        this.officeService.updateEmployee(employee);
    }

    @Override
    public AllEmployeeResponse allEmployee(@RequestBody AllEmployeeFilter allEmployeeFilter) {
        return this.officeService.allEmployee(allEmployeeFilter);
    }

    @Override
    public void createVehicle(@RequestBody Vehicle vehicle) {
        this.officeService.createVehicle(vehicle);
    }

    @Override
    public void deleteVehicle(@PathVariable("vin") String vin) {
        this.officeService.deleteVehicle(vin);
    }

    @Override
    public void updateVehicle(@PathVariable("vin") String vin, @RequestBody Vehicle vehicle) {
        vehicle.setVin(vin);
        this.officeService.updateVehicle(vehicle);
    }

    @Override
    public void verifyReservation(@PathVariable("reservationId") int reservationId, @RequestBody String verifyIt) {
        this.officeService.verifyReservation(reservationId, Boolean.parseBoolean(verifyIt));
    }

    @Override
    public OpenReservationResponse openReservationRequest() {
        return this.officeService.openReservationRequest();
    }
}
