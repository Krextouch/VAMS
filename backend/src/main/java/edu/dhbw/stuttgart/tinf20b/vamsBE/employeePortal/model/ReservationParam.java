package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

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
    private int employeeId;
}
