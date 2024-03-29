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
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        if(employeeRepository.existsById(employee.getEmployeeId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee does exist");
        } else {
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
    }

    public void updateEmployee(Employee employee) {
        if(!(employeeRepository.existsById(employee.getEmployeeId()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee does not exist");
        } else {
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
    }

    public void deleteEmployee(int employeeId) {
        Optional<Employee> employee = this.employeeRepository.findByEmployeeId(employeeId);
        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee does not exist");
        }
        this.employeeRepository.delete(employee.get());
    }

    public void createVehicle(Vehicle vehicle) {
        if(vehicleRepository.existsById(vehicle.getVin())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle does exist");
        } else {
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
    }

    public void updateVehicle(Vehicle vehicle) {
        if(!(vehicleRepository.existsById(vehicle.getVin()))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle does not exist");
        } else {
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
    }

    public void deleteVehicle(String vin) {
        Optional<Vehicle> vehicle = this.vehicleRepository.findByVin(vin);
        if (vehicle.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle does not exist");
        }
        this.vehicleRepository.delete(vehicle.get());
    }

    public void verifyReservation(int reservationId, boolean verifyIt) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation does not exist");
        } else {
            Reservation reservation = Reservation.builder()
                    .id(reservationId)
                    .startTimeOfReservation(reservationRepository.findById(reservationId).get().getStartTimeOfReservation())
                    .endTimeOfReservation(reservationRepository.findById(reservationId).get().getEndTimeOfReservation())
                    .vehicle(reservationRepository.findById(reservationId).get().getVehicle())
                    .employee(reservationRepository.findById(reservationId).get().getEmployee())
                    .isVerified(verifyIt)
                    .build();

            this.reservationRepository.save(reservation);
        }

    }

    public OpenReservationResponse openReservationRequest() {
        List<Reservation> allVehicleWithoutVerification = new ArrayList<>();
        allVehicleWithoutVerification.addAll(reservationRepository.findByIsVerifiedFalse().get());
        List<OpenReservationParam> openReservationParamList = new ArrayList<>();

        for (Reservation reservation : allVehicleWithoutVerification) {
            OpenReservationParam openReservationParam = OpenReservationParam.builder()
                    .reservationId(reservation.getId())
                    .startTimeOfReservation(reservation.getStartTimeOfReservation())
                    .endTimeOfReservation(reservation.getEndTimeOfReservation())
                    .vehicleVin(reservation.getVehicle().getVin())
                    .build();

            openReservationParamList.add(openReservationParam);
        }

        return new OpenReservationResponse(openReservationParamList);
    }

    public AllEmployeeResponse allEmployee(AllEmployeeFilter allEmployeeFilter) {
        List<AllEmployeeParam> employeeList = new ArrayList<>();

        for (Employee employee : employeeRepository.findAll()) {
            List<ReservationParam> reservationList = new ArrayList<>();
            for (Reservation reservation : employee.getReservation()) {
                ReservationParam reservationParam = ReservationParam.builder()
                        .id(reservation.getId())
                        .startTimeOfReservation(reservation.getStartTimeOfReservation())
                        .endTimeOfReservation(reservation.getEndTimeOfReservation())
                        .isVerified(reservation.getIsVerified())
                        .vehicleVin(reservation.getVehicle().getVin())
                        .employeeId(reservation.getEmployee().getEmployeeId())
                        .build();

                reservationList.add(reservationParam);
            }

            AllEmployeeParam addEmployee = AllEmployeeParam.builder()
                    .employeeId(employee.getEmployeeId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .email(employee.getEmail())
                    .nameTag(employee.getNameTag())
                    .password(employee.getPassword())
                    .workCard(employee.getWorkCard())
                    .birthday(employee.getBirthday())
                    .birthplace(employee.getBirthplace())
                    .hasOfficeRights(employee.isHasOfficeRights())
                    .hasDrivingLicense(employee.isHasDrivingLicense())
                    .reservation(reservationList)
                    .build();

            employeeList.add(addEmployee);
        }

        return new AllEmployeeResponse(applyFilters(employeeList, allEmployeeFilter));
    }

    private List<AllEmployeeParam> applyFilters(List<AllEmployeeParam> allEmployeeParamList, AllEmployeeFilter allEmployeeFilter) {

        //firstName
        if (allEmployeeFilter.getFirstName() != null) {
            if(!(allEmployeeFilter.getFirstName().isBlank())) {
                List<AllEmployeeParam> tmpAllEmployeeParamList = new ArrayList<>();

                for (AllEmployeeParam allEmployeeParam : allEmployeeParamList) {
                    if (allEmployeeParam.getFirstName().toLowerCase().contains(allEmployeeFilter.getFirstName().toLowerCase())) {
                        tmpAllEmployeeParamList.add(allEmployeeParam);
                    }
                }

                allEmployeeParamList.clear();
                allEmployeeParamList.addAll(tmpAllEmployeeParamList);
            }
        }

        //lastName
        if (allEmployeeFilter.getLastName() != null) {
            if(!(allEmployeeFilter.getLastName().isBlank())) {
                List<AllEmployeeParam> tmpAllEmployeeParamList = new ArrayList<>();

                for (AllEmployeeParam allEmployeeParam : allEmployeeParamList) {
                    if (allEmployeeParam.getLastName().toLowerCase().contains(allEmployeeFilter.getLastName().toLowerCase())) {
                        tmpAllEmployeeParamList.add(allEmployeeParam);
                    }
                }

                allEmployeeParamList.clear();
                allEmployeeParamList.addAll(tmpAllEmployeeParamList);
            }
        }

        //email
        if (allEmployeeFilter.getEmail() != null) {
            if(!(allEmployeeFilter.getEmail().isBlank())) {
                List<AllEmployeeParam> tmpAllEmployeeParamList = new ArrayList<>();

                for (AllEmployeeParam allEmployeeParam : allEmployeeParamList) {
                    if (allEmployeeParam.getEmail().toLowerCase().contains(allEmployeeFilter.getEmail().toLowerCase())) {
                        tmpAllEmployeeParamList.add(allEmployeeParam);
                    }
                }

                allEmployeeParamList.clear();
                allEmployeeParamList.addAll(tmpAllEmployeeParamList);
            }
        }

        //nameTag
        if (allEmployeeFilter.getNameTag() != null) {
            if(!(allEmployeeFilter.getNameTag().isBlank())) {
                List<AllEmployeeParam> tmpAllEmployeeParamList = new ArrayList<>();

                for (AllEmployeeParam allEmployeeParam : allEmployeeParamList) {
                    if (allEmployeeParam.getNameTag().toLowerCase().contains(allEmployeeFilter.getNameTag().toLowerCase())) {
                        tmpAllEmployeeParamList.add(allEmployeeParam);
                    }
                }

                allEmployeeParamList.clear();
                allEmployeeParamList.addAll(tmpAllEmployeeParamList);
            }
        }

        return allEmployeeParamList;
    }

    public String resetPassword(ResetPasswordParam resetPasswordParam) {
        String name = resetPasswordParam.getEmployeeName();
        String newPassword = "";
        Employee employee;

        if (name.contains("@")) {
            employee = employeeRepository.findByEmail(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        } else if (name.matches("^[0-9]*$")) {
            employee = employeeRepository.findByEmployeeId(Integer.parseInt(name)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        } else {
            employee = employeeRepository.findByNameTag(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        }

        if (!(employee == null)) {
            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 32;
            Random random = new Random();

            newPassword = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            Employee updatedEmployee = Employee.builder()
                    .employeeId(employee.getEmployeeId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .email(employee.getEmail())
                    .nameTag(employee.getNameTag())
                    .password(BCrypt.hashpw(newPassword, BCrypt.gensalt()))
                    .workCard(employee.getWorkCard())
                    .birthday(employee.getBirthday())
                    .birthplace(employee.getBirthplace())
                    .hasDrivingLicense(employee.isHasDrivingLicense())
                    .hasOfficeRights(employee.isHasOfficeRights())
                    .reservation(employee.getReservation())
                    .build();

            this.employeeRepository.save(updatedEmployee);
        }
        return newPassword;
    }
}
