package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationTimeframe {

    private LocalDateTime startTimeOfReservation;
    private LocalDateTime endTimeOfReservation;
}
