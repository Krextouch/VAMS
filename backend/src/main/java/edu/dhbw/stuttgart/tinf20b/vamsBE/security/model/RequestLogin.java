package edu.dhbw.stuttgart.tinf20b.vamsBE.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestLogin {
    private String username;
    private String password;
}
