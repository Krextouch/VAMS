package edu.dhbw.stuttgart.tinf20b.vamsBE.core.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, String> {
    Optional<Vehicle> findByVin(String vin);
    Optional<Vehicle> findByLicensePlate(String license_plate);
}
