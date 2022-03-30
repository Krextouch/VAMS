package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OpenReservationParam {

    private int reservationId;
    private LocalDateTime startTimeOfReservation;
    private LocalDateTime endTimeOfReservation;
    private String vehicleVin;
}
