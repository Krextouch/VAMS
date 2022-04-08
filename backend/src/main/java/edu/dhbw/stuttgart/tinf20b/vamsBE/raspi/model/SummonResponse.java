package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummonResponse {

    private boolean authorizedDriving;
    private boolean authorizedOpening;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime beginn;
    private LocalDateTime end;

}
