package edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.RaspiLog;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @Column(name = "employee_id")
    private int employeeId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "name_tag", nullable = false, unique = true)
    private String nameTag;
    @Column(nullable = false)
    private String password;
    @Column(name = "work_card", unique = true)
    private String workCard;
    @Column(nullable = false)
    private LocalDate birthday;
    private String birthplace;
    @Column(name = "has_driving_license")
    private boolean hasDrivingLicense;
    @Column(name = "has_office_rights")
    private boolean hasOfficeRights;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservation = new LinkedList<>();
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RaspiLog> raspiLogs = new LinkedList<>();
}
