package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {
    Optional<Device> findByDeviceId(int id);

    Optional<Device> findByVin(Vehicle vehicle);

    Optional<Device> findByToken(String token);
}
