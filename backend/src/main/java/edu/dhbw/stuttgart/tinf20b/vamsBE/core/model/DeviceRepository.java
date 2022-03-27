package edu.dhbw.stuttgart.tinf20b.vamsBE.core.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {
    Optional<Device> findById(int id);
    Optional<Device> findByVin(Vehicle vehicle);
    Optional<Device> findByToken(String token);
}
