package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummonRequest {

    private int deviceId;
    private String token;
    private String workCard;

}
