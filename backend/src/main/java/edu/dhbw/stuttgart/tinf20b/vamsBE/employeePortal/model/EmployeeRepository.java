package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Optional<Employee> findByEmployeeId(int employeeId);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByNameTag(String nameTag);
    Optional<Employee> findByBirthday(LocalDate birthday);
    Optional<Employee> findByWorkCard(String workCard);
}
