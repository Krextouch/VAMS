package edu.dhbw.stuttgart.tinf20b.vamsBE.core.model;

import lombok.*;

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
    private String VIN;
    private String License_Plate;
    private String Brand;
    private String Model;
    private int PS;
    private String Color;
    private LocalDate First_Registration;
}
