package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class AvailableVehicleResponse {

    public List<AvailableVehicleParam> availableVehicleParamList = new LinkedList<>();
}
