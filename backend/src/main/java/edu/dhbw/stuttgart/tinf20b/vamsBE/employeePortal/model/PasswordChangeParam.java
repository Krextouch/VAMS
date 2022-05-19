package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordChangeParam {

    String oldPassword;
    String newPassword;
}
