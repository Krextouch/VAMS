package edu.dhbw.stuttgart.tinf20b.vamsBE.core.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "start_time_of_reservation")
    private LocalDateTime startTimeOfReservation;
    @Column(name = "end_time_of_reservation")
    private LocalDateTime endTimeOfReservation;
    @Column(name = "isVerified")
    private Boolean isVerified;

    @ManyToOne
    @JoinColumn(name = "vin")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
