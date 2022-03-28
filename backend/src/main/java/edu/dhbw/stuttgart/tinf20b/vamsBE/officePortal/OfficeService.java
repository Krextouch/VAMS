package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.VehicleRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class OfficeService {

    private final EmployeeRepository employeeRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public OfficeService(EmployeeRepository employeeRepository, VehicleRepository vehicleRepository) {
        this.employeeRepository = employeeRepository;
        this.vehicleRepository = vehicleRepository;
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
