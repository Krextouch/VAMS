package edu.dhbw.stuttgart.tinf20b.vamsBE.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = "vin")
    private Vehicle vehicle;
    @Column(name = "start_time_of_reservation")
    private LocalDateTime startTimeOfReservation;
    @Column(name = "end_time_of_reservation")
    private LocalDateTime endTimeOfReservation;
}
