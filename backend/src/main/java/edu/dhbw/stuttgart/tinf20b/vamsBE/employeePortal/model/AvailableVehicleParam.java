package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableVehicleParam {

    private String brand;
    private String model;
    private int ps;
    private String color;
}
