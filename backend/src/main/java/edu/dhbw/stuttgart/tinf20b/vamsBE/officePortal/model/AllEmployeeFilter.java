package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllEmployeeFilter {

    private String firstName;
    private String lastName;
    private String email;
    private String nameTag;
}
