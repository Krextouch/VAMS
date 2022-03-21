package edu.dhbw.stuttgart.tinf20b.vamsBE.core;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    //delete prod only for test
    public void setVehicleTest(){
        Vehicle test = Vehicle.builder()
                .VIN("42069")
                .Brand("Mercedes-Benz")
                .Model("AMG GT 63 4-Türer 4MATIC")
                .PS(572)
                .Color("blue")
                .License_Plate("S-MB 1337")
                .First_Registration(LocalDate.of(2020, 01, 01))
                .build();

        this.vehicleRepository.save(test);
    }
}
