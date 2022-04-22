package edu.dhbw.stuttgart.tinf20b.vamsBE.core;

import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.DeviceRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private DeviceRepository deviceRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, DeviceRepository deviceRepository) {
        this.vehicleRepository = vehicleRepository;
        this.deviceRepository = deviceRepository;
    }
}
