package edu.dhbw.stuttgart.tinf20b.vamsBE.security.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "disabled_token")
public class DisabledToken {

    @Id
    private String token;
    private LocalDateTime expires;

}
