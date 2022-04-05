package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservationResponse {

    List<ReservationParam> reservationParamList = new ArrayList<>();

    public ReservationResponse(List<ReservationParam> reservationParamList) {
        this.reservationParamList = reservationParamList;
    }
}
