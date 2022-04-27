package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

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

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RaspiLog> raspiLogs = new LinkedList<>();
}
