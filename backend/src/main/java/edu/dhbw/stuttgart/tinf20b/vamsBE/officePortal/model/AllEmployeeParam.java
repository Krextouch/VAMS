package edu.dhbw.stuttgart.tinf20b.vamsBE.officePortal.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.ReservationParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
public class AllEmployeeParam {

    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String nameTag;
    private String password;
    private String workCard;
    private LocalDate birthday;
    private String birthplace;
    private boolean hasDrivingLicense;
    private boolean hasOfficeRights;
    private List<ReservationParam> reservation = new LinkedList<>();
}
