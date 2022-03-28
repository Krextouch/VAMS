package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.ReservationRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ReservationRepository reservationRepository) {
        this.employeeRepository = employeeRepository;
        this.reservationRepository = reservationRepository;
    }

    public void createReservation(Reservation reservation) {
        Reservation newReservation = Reservation.builder()
                .startTimeOfReservation(reservation.getStartTimeOfReservation())
                .endTimeOfReservation(reservation.getEndTimeOfReservation())
                .vehicle(reservation.getVehicle())
                .build();

        this.reservationRepository.save(newReservation);
    }
}
