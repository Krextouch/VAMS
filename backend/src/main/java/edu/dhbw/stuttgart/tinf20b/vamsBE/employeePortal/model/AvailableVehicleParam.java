package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AvailableVehicleParam {

    private String brand;
    private String model;
    private int ps;
    private String color;
}
