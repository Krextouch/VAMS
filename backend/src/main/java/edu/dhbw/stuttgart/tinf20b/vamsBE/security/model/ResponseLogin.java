package edu.dhbw.stuttgart.tinf20b.vamsBE.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLogin {

    private String token;
    private LocalDateTime expiration;
    private int employeeId;
    private String email;
    private String nameTag;
    private String firstName;
    private String lastName;
    private boolean hasDrivingLicense;
    private boolean hasOfficeRights;
}
