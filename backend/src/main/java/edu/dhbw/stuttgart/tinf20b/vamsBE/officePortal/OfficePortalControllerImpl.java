package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OfficePortalControllerImpl implements OfficePortalController{

    private final VehicleService vehicleService;

    @Autowired
    public OfficePortalControllerImpl(VehicleService vehicleService){
       this.vehicleService = vehicleService;
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
}
