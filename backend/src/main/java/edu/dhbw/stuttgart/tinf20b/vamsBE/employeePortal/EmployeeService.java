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
        Employee employee = getEmployeeFromToken(authorization);
        Reservation newReservation = Reservation.builder()
                .startTimeOfReservation(reservation.getStartTimeOfReservation())
                .endTimeOfReservation(reservation.getEndTimeOfReservation())
                .vehicle(reservation.getVehicle())
                .employee(employee)
                .isVerified(employee.isHasOfficeRights())
                .build();

        this.reservationRepository.save(newReservation);
    }

    public void deleteReservation(Reservation reservation, String authorization) {
        Employee employee = getEmployeeFromToken(authorization);
        if (!(employee.getEmployeeId() == reservation.getEmployee().getEmployeeId())) {
            return;
        } else if (!employee.isHasOfficeRights()) {
            return;
        }
        this.reservationRepository.deleteById(reservation.getId());
    }

    public AvailableVehicleResponse getAvailableVehicle(ReservationTimeframe reservationTimeframe) {
        List<AvailableVehicleParam> availableVehicleParamList = new ArrayList<>();
        boolean vehicleAvailable;

        for (Vehicle vehicle : vehicleRepository.findAll()) {
            vehicleAvailable = true;
            for (Reservation reservation : vehicle.getReservation()) {
                if ((reservation.getEndTimeOfReservation().isAfter(reservationTimeframe.getStartTimeOfReservation())
                        && reservation.getStartTimeOfReservation().isBefore(reservationTimeframe.getStartTimeOfReservation()))
                        || (reservation.getEndTimeOfReservation().isAfter(reservationTimeframe.getEndTimeOfReservation())
                        && reservation.getStartTimeOfReservation().isBefore(reservationTimeframe.getEndTimeOfReservation()))) {
                    vehicleAvailable = false;
                    break;
                }
            }

            if (vehicleAvailable) {
                AvailableVehicleParam availableVehicleParam = new AvailableVehicleParam();
                availableVehicleParam.setBrand(vehicle.getBrand());
                availableVehicleParam.setColor(vehicle.getColor());
                availableVehicleParam.setModel(vehicle.getModel());
                availableVehicleParam.setPs(vehicle.getPs());

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
            SingleEmployeeReservationParam serp = new SingleEmployeeReservationParam();

            serp.setId(reservation.getId());
            serp.setStartTimeOfReservation(reservation.getStartTimeOfReservation());
            serp.setEndTimeOfReservation(reservation.getEndTimeOfReservation());
            serp.setIsVerified(reservation.getIsVerified());
            serp.setVehicleVin(reservation.getVehicle().getVin());

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
            ReservationParam rP = new ReservationParam();

            rP.setId(reservation.getId());
            rP.setStartTimeOfReservation(reservation.getStartTimeOfReservation());
            rP.setEndTimeOfReservation(reservation.getEndTimeOfReservation());
            rP.setIsVerified(reservation.getIsVerified());
            rP.setVehicleVin(reservation.getVehicle().getVin());
            rP.setEmployeeId(reservation.getEmployee().getEmployeeId());

            reservationParamList.add(rP);
        }

        return new ReservationResponse(applyFilters(reservationParamList, reservationFilter));
    }

    public List<ReservationParam> applyFilters(List<ReservationParam> reservationParamList, ReservationFilter reservationFilter) {

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

        return reservationParamList;
    }

    public String passwordReset(PasswordResetParam passwordResetParam) {
        String name = passwordResetParam.getName();
        String newPassword = "";
        Employee employee;

        if (name.contains("@")) {
            employee = employeeRepository.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        } else if (name.matches("^[0-9]*$")) {
            employee = employeeRepository.findByEmployeeId(Integer.parseInt(name)).orElseThrow(() -> new UsernameNotFoundException("Employee id not found"));
        } else {
            employee = employeeRepository.findByNameTag(name).orElseThrow(() -> new UsernameNotFoundException("Name tag not found"));
        }

        if(!(employee == null)) {
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
