package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.ReservationRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.VehicleRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.*;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void createReservation(Reservation reservation) {
        Reservation newReservation = Reservation.builder()
                .startTimeOfReservation(reservation.getStartTimeOfReservation())
                .endTimeOfReservation(reservation.getEndTimeOfReservation())
                .vehicle(reservation.getVehicle())
                .employee(reservation.getEmployee())
                .isVerified(employeeRepository.findByEmployeeId(reservation.getEmployee().getEmployeeId()).get().isHasOfficeRights())
                .build();

        this.reservationRepository.save(newReservation);
    }

    public void deleteReservation(Reservation reservation) {
        //Es dürfen nur eigene Reservations gelöscht werden. Mach bitte ein Check, ob es die Reservation vom ankommenden User ist.
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
            token =  token.substring(7);
        } else {
            throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED, "No token found");
        }

        String email = jwtTokenProvider.getUserMailFromToken(token);
        Optional<Employee> employee = employeeRepository.findByEmail(email);


        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }
}
