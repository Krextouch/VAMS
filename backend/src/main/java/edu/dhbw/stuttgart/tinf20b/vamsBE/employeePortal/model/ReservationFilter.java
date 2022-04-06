package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationFilter {

    private LocalDateTime startTimeFrame;
    private LocalDateTime endTimeFrame;
    private Boolean isVerified;
    private String vehicleVin;
    private int employeeId;
    private boolean showAllEmployees;
}
