package edu.dhbw.stuttgart.tinf20b.vamsBE.core.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    private String vin;
    @Column(name = "license_plate", nullable = false)
    private String licensePlate;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    private int ps;
    private String color;
    @Column(name = "first_registration", nullable = false)
    private LocalDate firstRegistration;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservation = new LinkedList<>();
}
