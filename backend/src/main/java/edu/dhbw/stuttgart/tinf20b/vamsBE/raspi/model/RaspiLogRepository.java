package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RaspiLogRepository extends CrudRepository<RaspiLog, Integer> {
    Optional<List<RaspiLog>> findByDevice(Device employee);
    Optional<List<RaspiLog>> findByEmployee(Employee employee);
    Optional<List<RaspiLog>> findByTimestampIsBefore(LocalDateTime timestamp);
}
