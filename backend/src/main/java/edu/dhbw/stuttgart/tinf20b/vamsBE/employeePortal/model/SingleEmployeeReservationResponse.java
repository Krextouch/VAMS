package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SingleEmployeeReservationResponse {

    List<SingleEmployeeReservationParam> singleEmployeeReservationParamList = new ArrayList<>();

    public SingleEmployeeReservationResponse(List<SingleEmployeeReservationParam> singleEmployeeReservationParamList) {
        this.singleEmployeeReservationParamList = singleEmployeeReservationParamList;
    }
}
