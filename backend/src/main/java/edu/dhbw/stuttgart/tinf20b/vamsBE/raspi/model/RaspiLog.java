package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "raspi_log")
public class RaspiLog {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int logId;
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;
    @Column(name = "work_card")
    private String workCard;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private LocalDateTime timestamp;
    @Column(name = "authorized_opening")
    private boolean authorizedOpening;
    @Column(name = "authorized_driving")
    private boolean authorizedDriving;
}
