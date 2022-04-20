package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class ReservationParam {

    private int id;
    private LocalDateTime startTimeOfReservation;
    private LocalDateTime endTimeOfReservation;
    private Boolean isVerified;
    private String vehicleVin;
    private int employeeId;
}
