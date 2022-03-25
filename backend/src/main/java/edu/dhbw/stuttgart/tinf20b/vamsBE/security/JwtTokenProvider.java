package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret")
    private String jwtSecret;

    public String generateToken(String employeeEmail){
        Instant now = Instant.now();
        Instant expiration = now.plus(10, ChronoUnit.HOURS);

        return Jwts.builder()
                .setSubject(employeeEmail)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return generateToken(user.getUsername());
        //video 13:00 https://www.youtube.com/watch?v=mn5UZYtPLjg
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
