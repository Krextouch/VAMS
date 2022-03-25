package edu.dhbw.stuttgart.tinf20b.vamsBE.security.model;

import lombok.Data;

@Data
public class RequestLogin {
    private String username;
    private String password;
}
