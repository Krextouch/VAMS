package edu.dhbw.stuttgart.tinf20b.vamsBE.core;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Device;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.DeviceRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private DeviceRepository deviceRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, DeviceRepository deviceRepository) {
        this.vehicleRepository = vehicleRepository;
        this.deviceRepository = deviceRepository;
    }

    //delete prod only for test
    public void setVehicleTest() {
        Vehicle test = Vehicle.builder()
                .vin("42069")
                .brand("Mercedes-Benz")
                .model("AMG GT 63 4-Türer 4MATIC")
                .ps(572)
                .color("blue")
                .licensePlate("S-MB 1337")
                .firstRegistration(LocalDate.of(2020, 01, 01))
                .build();

        this.vehicleRepository.save(test);

        Device device = Device.builder()
                .vin(test)
                .token("alwdjkhgawldhawgd32kjgel2hdl321kdbblbd12zd1b23lb123123h123")
                .build();

        this.deviceRepository.save(device);

    }

    public void createVehicle(Vehicle vehicle) {
        Vehicle newVehicle = Vehicle.builder()
                .vin(vehicle.getVin())
                .licensePlate(vehicle.getLicensePlate())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .ps(vehicle.getPs())
                .color(vehicle.getColor())
                .firstRegistration(vehicle.getFirstRegistration())
                .build();

        this.vehicleRepository.save(newVehicle);
    }

    public void deleteVehicle(Vehicle vehicle) {
        this.vehicleRepository.deleteById(vehicle.getVin());
    }
}
