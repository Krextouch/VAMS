package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.ReservationRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.VehicleRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.*;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;
    private final VehicleRepository vehicleRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           ReservationRepository reservationRepository,
                           VehicleRepository vehicleRepository,
                           JwtTokenProvider jwtTokenProvider) {
        this.employeeRepository = employeeRepository;
        this.reservationRepository = reservationRepository;
        this.vehicleRepository = vehicleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void createReservation(Reservation reservation, String authorization) {
        if (!(reservationRepository.existsById(reservation.getId()))) {
            Employee employee = getEmployeeFromToken(authorization);
            Reservation newReservation = Reservation.builder()
                    .startTimeOfReservation(reservation.getStartTimeOfReservation())
                    .endTimeOfReservation(reservation.getEndTimeOfReservation())
                    .vehicle(reservation.getVehicle())
                    .employee(employee)
                    .isVerified(employee.isHasOfficeRights())
                    .build();

            this.reservationRepository.save(newReservation);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Reservation already exists");
        }
    }

    public void updateReservation(Reservation reservation, String authorization) {
        if (reservationRepository.existsById(reservation.getId())) {
            Employee employee = getEmployeeFromToken(authorization);
            Reservation newReservation = Reservation.builder()
                    .id(reservation.getId())
                    .startTimeOfReservation(reservation.getStartTimeOfReservation())
                    .endTimeOfReservation(reservation.getEndTimeOfReservation())
                    .vehicle(reservation.getVehicle())
                    .employee(employee)
                    .isVerified(employee.isHasOfficeRights())
                    .build();

            this.reservationRepository.save(newReservation);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation does not exist");
        }

    }

    public void deleteReservation(int reservationId, String authorization) {
        Employee employee = getEmployeeFromToken(authorization);
        Optional<Reservation> reservation = this.reservationRepository.findById(reservationId);
        if (reservation.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!(employee.getEmployeeId() == reservation.get().getEmployee().getEmployeeId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Employee does not match to reservation");
        } else if (!employee.isHasOfficeRights()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No office rights");
        }
        this.reservationRepository.deleteById(reservation.get().getId());
    }

    public AvailableVehicleResponse getAvailableVehicle(LocalDateTime startTime, LocalDateTime endTime) {
        List<AvailableVehicleParam> availableVehicleParamList = new ArrayList<>();
        boolean vehicleAvailable;

        for (Vehicle vehicle : vehicleRepository.findAll()) {
            vehicleAvailable = true;
            for (Reservation reservation : vehicle.getReservation()) {
                if ((reservation.getEndTimeOfReservation().isAfter(startTime)
                        && reservation.getStartTimeOfReservation().isBefore(startTime))
                        || (reservation.getEndTimeOfReservation().isAfter(endTime)
                        && reservation.getStartTimeOfReservation().isBefore(endTime))) {
                    vehicleAvailable = false;
                    break;
                }
            }

            if (vehicleAvailable) {
                AvailableVehicleParam availableVehicleParam = AvailableVehicleParam.builder()
                        .vin(vehicle.getVin())
                        .brand(vehicle.getBrand())
                        .color(vehicle.getColor())
                        .model(vehicle.getModel())
                        .ps(vehicle.getPs())
                        .build();

                availableVehicleParamList.add(availableVehicleParam);
            }
        }
        AvailableVehicleResponse availableVehicleResponse = new AvailableVehicleResponse();
        availableVehicleResponse.setAvailableVehicleParamList(availableVehicleParamList);
        return availableVehicleResponse;
    }

    public Employee getEmployeeFromToken(String token) {

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No token found");
        }

        String email = jwtTokenProvider.getUserMailFromToken(token);
        Optional<Employee> employee = employeeRepository.findByEmail(email);


        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }

    public SingleEmployeeReservationResponse getReservatedVehicle(String authorization) {
        List<SingleEmployeeReservationParam> responseSerp = new ArrayList<>();

        for (Reservation reservation : reservationRepository.findAllByEmployee(getEmployeeFromToken(authorization)).get()) {
            SingleEmployeeReservationParam serp = SingleEmployeeReservationParam.builder()
                    .id(reservation.getId())
                    .startTimeOfReservation(reservation.getStartTimeOfReservation())
                    .endTimeOfReservation(reservation.getEndTimeOfReservation())
                    .isVerified(reservation.getIsVerified())
                    .vehicleVin(reservation.getVehicle().getVin())
                    .build();

            responseSerp.add(serp);
        }

        return new SingleEmployeeReservationResponse(responseSerp);
    }

    public ReservationResponse allReservations(ReservationFilter reservationFilter, String authorization) {
        List<ReservationParam> reservationParamList = new ArrayList<>();
        Employee employee = getEmployeeFromToken(authorization);

        for (Reservation reservation : reservationRepository.findAll()) {
            if (!(reservationFilter.isShowAllEmployees() && employee.isHasOfficeRights())) {
                if (!(employee.equals(reservation.getEmployee()))) continue;
            }
            ReservationParam rP = ReservationParam.builder()
                    .id(reservation.getId())
                    .startTimeOfReservation(reservation.getStartTimeOfReservation())
                    .endTimeOfReservation(reservation.getEndTimeOfReservation())
                    .isVerified(reservation.getIsVerified())
                    .vehicleVin(reservation.getVehicle().getVin())
                    .employeeId(reservation.getEmployee().getEmployeeId())
                    .build();

            reservationParamList.add(rP);
        }

        return new ReservationResponse(applyFilters(reservationParamList, reservationFilter, employee));
    }

    public List<ReservationParam> applyFilters(List<ReservationParam> reservationParamList, ReservationFilter reservationFilter, Employee employee) {

        if (reservationFilter.isShowAllEmployees() && employee.isHasOfficeRights()) {
            if (reservationFilter.getEmployeeId() != null) {
                if (!(reservationFilter.getEmployeeId().isBlank())) {
                    List<ReservationParam> tmpReservationParamList = new ArrayList<>(reservationParamList);
                    int i = 0;
                    int removedObjects = 0;
                    for (ReservationParam tmpReservationParam : tmpReservationParamList) {
                        if (!(Integer.parseInt(reservationFilter.getEmployeeId()) == tmpReservationParam.getEmployeeId())) {
                            reservationParamList.remove(i - removedObjects);
                            removedObjects++;
                        }
                        i++;
                    }
                }
            }
        }

        if (reservationFilter.getStartTimeFrame() != null) {
            List<ReservationParam> tmpReservationParamList = new ArrayList<>(reservationParamList);
            int i = 0;
            int removedObjects = 0;
            for (ReservationParam tmpReservationParam : tmpReservationParamList) {
                if (!(reservationFilter.getStartTimeFrame().isBefore(tmpReservationParam.getStartTimeOfReservation())
                        || reservationFilter.getStartTimeFrame().isEqual(tmpReservationParam.getStartTimeOfReservation()))) {
                    reservationParamList.remove(i - removedObjects);
                    removedObjects++;
                }
                i++;
            }
        }

        if (reservationFilter.getEndTimeFrame() != null) {
            List<ReservationParam> tmpReservationParamList = new ArrayList<>(reservationParamList);
            int i = 0;
            int removedObjects = 0;
            for (ReservationParam tmpReservationParam : tmpReservationParamList) {
                if (!(reservationFilter.getEndTimeFrame().isAfter(tmpReservationParam.getEndTimeOfReservation())
                        || reservationFilter.getEndTimeFrame().isEqual(tmpReservationParam.getEndTimeOfReservation()))) {
                    reservationParamList.remove(i - removedObjects);
                    removedObjects++;
                }
                i++;
            }
        }

        if (reservationFilter.getIsVerified() != null) {
            List<ReservationParam> tmpReservationParamList = new ArrayList<>(reservationParamList);
            int i = 0;
            int removedObjects = 0;
            for (ReservationParam tmpReservationParam : tmpReservationParamList) {
                if (!(reservationFilter.getIsVerified() == tmpReservationParam.getIsVerified())) {
                    reservationParamList.remove(i - removedObjects);
                    removedObjects++;
                }
                i++;
            }
        }

        if (reservationFilter.getVehicleVin() != null) {
            if (!(reservationFilter.getVehicleVin().isBlank())) {
                List<ReservationParam> tmpReservationParamList = new ArrayList<>(reservationParamList);
                int i = 0;
                int removedObjects = 0;
                for (ReservationParam tmpReservationParam : tmpReservationParamList) {
                    if (!(reservationFilter.getVehicleVin().equals(tmpReservationParam.getVehicleVin()))) {
                        reservationParamList.remove(i - removedObjects);
                        removedObjects++;
                    }
                    i++;
                }
            }
        }

        return reservationParamList;
    }

    public void passwordChange(PasswordChangeParam passwordChangeParam, String authorization) {
        Employee employee = getEmployeeFromToken(authorization);

        if (BCrypt.checkpw(passwordChangeParam.getOldPassword(), employee.getPassword())) {

            Employee updatedEmployee = Employee.builder()
                    .employeeId(employee.getEmployeeId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .email(employee.getEmail())
                    .nameTag(employee.getNameTag())
                    .password(BCrypt.hashpw(passwordChangeParam.getNewPassword(), BCrypt.gensalt()))
                    .workCard(employee.getWorkCard())
                    .birthday(employee.getBirthday())
                    .birthplace(employee.getBirthplace())
                    .hasDrivingLicense(employee.isHasDrivingLicense())
                    .hasOfficeRights(employee.isHasOfficeRights())
                    .reservation(employee.getReservation())
                    .build();
            this.employeeRepository.save(updatedEmployee);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Old password does not match");
        }
    }
}
