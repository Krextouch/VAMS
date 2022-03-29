package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvailableVehicleParam {

    private String brand;
    private String model;
    private int ps;
    private String color;
}
