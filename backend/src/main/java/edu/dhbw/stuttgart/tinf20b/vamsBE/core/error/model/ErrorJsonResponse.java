package edu.dhbw.stuttgart.tinf20b.vamsBE.core.error.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorJsonResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
