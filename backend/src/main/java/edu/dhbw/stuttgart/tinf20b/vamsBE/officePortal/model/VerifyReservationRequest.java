package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifyReservationRequest {

    private int reservationId;
    private boolean verifyIt;
}
