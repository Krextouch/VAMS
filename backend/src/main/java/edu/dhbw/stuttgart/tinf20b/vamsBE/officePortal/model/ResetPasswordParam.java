package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordParam {

    String employeeName;
}
