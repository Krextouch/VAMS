package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class OfficeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public OfficeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void createEmployee(Employee employee) {
        Employee newEmployee = Employee.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .nameTag(employee.getNameTag())
                .password(BCrypt.hashpw(employee.getPassword(), BCrypt.gensalt()))
                .workCard(employee.getWorkCard())
                .birthday(employee.getBirthday())
                .birthplace(employee.getBirthplace())
                .hasDrivingLicense(employee.isHasDrivingLicense())
                .hasOfficeRights(employee.isHasOfficeRights())
                .build();

        this.employeeRepository.save(newEmployee);
    }

    public void deleteEmployee(Employee employee) {
        this.employeeRepository.deleteById(employee.getEmployeeId());
    }
}
