package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.ReservationRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.VehicleRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.ReservationParam;
import edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfficeService {

    private final EmployeeRepository employeeRepository;
    private final VehicleRepository vehicleRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public OfficeService(EmployeeRepository employeeRepository, VehicleRepository vehicleRepository, ReservationRepository reservationRepository) {
        this.employeeRepository = employeeRepository;
        this.vehicleRepository = vehicleRepository;
        this.reservationRepository = reservationRepository;
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

    public void verifyReservation(VerifyReservationRequest verifyReservationRequest) {
        if (!verifyReservationRequest.isVerifyIt()) {
            this.reservationRepository.deleteById(verifyReservationRequest.getReservationId());
        } else {
            Reservation reservation = Reservation.builder()
                    .id(verifyReservationRequest.getReservationId())
                    .startTimeOfReservation(reservationRepository.findById(verifyReservationRequest.getReservationId()).get().getStartTimeOfReservation())
                    .endTimeOfReservation(reservationRepository.findById(verifyReservationRequest.getReservationId()).get().getEndTimeOfReservation())
                    .vehicle(reservationRepository.findById(verifyReservationRequest.getReservationId()).get().getVehicle())
                    .employee(reservationRepository.findById(verifyReservationRequest.getReservationId()).get().getEmployee())
                    .isVerified(verifyReservationRequest.isVerifyIt())
                    .build();

            this.reservationRepository.save(reservation);
        }

    }

    public OpenReservationResponse openReservationRequest() {
        List<Reservation> allVehicleWithoutVerification = new ArrayList<>();
        allVehicleWithoutVerification.addAll(reservationRepository.findByIsVerifiedFalse().get());
        List<OpenReservationParam> openReservationParamList = new ArrayList<>();

        for (Reservation reservation : allVehicleWithoutVerification) {
            OpenReservationParam openReservationParam = new OpenReservationParam();

            openReservationParam.setReservationId(reservation.getId());
            openReservationParam.setStartTimeOfReservation(reservation.getStartTimeOfReservation());
            openReservationParam.setEndTimeOfReservation(reservation.getEndTimeOfReservation());
            openReservationParam.setVehicleVin(reservation.getVehicle().getVin());

            openReservationParamList.add(openReservationParam);
        }

        return new OpenReservationResponse(openReservationParamList);
    }

    public AllEmployeeResponse allEmployee() {
        List<AllEmployeeParam> employeeList = new ArrayList<>();

        for (Employee employee : employeeRepository.findAll()) {
            List<ReservationParam> reservationList = new ArrayList<>();
            for (Reservation reservation : employee.getReservation()) {
                ReservationParam reservationParam = new ReservationParam();

                reservationParam.setId(reservation.getId());
                reservationParam.setStartTimeOfReservation(reservation.getStartTimeOfReservation());
                reservationParam.setEndTimeOfReservation(reservation.getEndTimeOfReservation());
                reservationParam.setIsVerified(reservation.getIsVerified());
                reservationParam.setVehicleVin(reservation.getVehicle().getVin());
                reservationParam.setEmployeeId(reservation.getEmployee().getEmployeeId());

                reservationList.add(reservationParam);
            }

            AllEmployeeParam addEmployee = new AllEmployeeParam();
            addEmployee.setEmployeeId(employee.getEmployeeId());
            addEmployee.setFirstName(employee.getFirstName());
            addEmployee.setLastName(employee.getLastName());
            addEmployee.setEmail(employee.getEmail());
            addEmployee.setNameTag(employee.getNameTag());
            addEmployee.setPassword(employee.getPassword());
            addEmployee.setWorkCard(employee.getWorkCard());
            addEmployee.setBirthday(employee.getBirthday());
            addEmployee.setBirthplace(employee.getBirthplace());
            addEmployee.setHasOfficeRights(employee.isHasOfficeRights());
            addEmployee.setHasDrivingLicense(employee.isHasDrivingLicense());
            addEmployee.setReservation(reservationList);

            employeeList.add(addEmployee);
        }

        return new AllEmployeeResponse(employeeList);
    }
}
