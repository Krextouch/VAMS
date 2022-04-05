package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationParam {

    private int id;
    private LocalDateTime startTimeOfReservation;
    private LocalDateTime endTimeOfReservation;
    private Boolean isVerified;
    private String vehicleVin;
}
