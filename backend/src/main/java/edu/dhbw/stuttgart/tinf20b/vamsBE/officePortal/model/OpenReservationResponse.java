package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OpenReservationResponse {
    List<OpenReservationParam> openReservationParamsList = new ArrayList<>();

    public OpenReservationResponse(List<OpenReservationParam> openReservationParams) {
        this.openReservationParamsList.addAll(openReservationParams);
    }
}
