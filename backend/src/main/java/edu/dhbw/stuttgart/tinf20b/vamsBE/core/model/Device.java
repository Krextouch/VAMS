package edu.dhbw.stuttgart.tinf20b.vamsBE.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @Column(name = "device_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int deviceId;
    @OneToOne
    @JoinColumn(name = "vin")
    private Vehicle vin;
    private String token;

}
