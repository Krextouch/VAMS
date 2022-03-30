package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.ReservationRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.VehicleRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.AvailableVehicleParam;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.AvailableVehicleResponse;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.ReservationTimeframe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ReservationRepository reservationRepository, VehicleRepository vehicleRepository) {
        this.employeeRepository = employeeRepository;
        this.reservationRepository = reservationRepository;
        this.vehicleRepository = vehicleRepository;
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
        this.reservationRepository.deleteById(reservation.getId());
    }

    public AvailableVehicleResponse getAvailableVehicle(ReservationTimeframe reservationTimeframe) {
        List<AvailableVehicleParam> availableVehicleParamList = new ArrayList<>();
        Boolean vehicleAvailable;

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
}
