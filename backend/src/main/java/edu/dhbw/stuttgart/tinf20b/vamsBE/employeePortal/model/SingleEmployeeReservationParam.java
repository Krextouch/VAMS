package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SingleEmployeeReservationParam {

    private int id;
    private LocalDateTime startTimeOfReservation;
    private LocalDateTime endTimeOfReservation;
    private Boolean isVerified;
    private String vehicleVin;
}
