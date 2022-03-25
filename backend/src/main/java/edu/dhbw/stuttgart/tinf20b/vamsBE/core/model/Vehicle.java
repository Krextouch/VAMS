package edu.dhbw.stuttgart.tinf20b.vamsBE.core.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Builder
@Data
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
}
