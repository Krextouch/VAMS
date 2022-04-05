package edu.dhbw.stuttgart.tinf20b.vamsBE.core.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    Optional<Reservation> findById(int id);
    Optional<List<Reservation>> findAllByEmployee(Employee employee);
    Optional<List<Reservation>> findByIsVerifiedFalse();
}
