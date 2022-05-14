package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    public String generateToken(String employeeEmail, List<String> roles, int counter) {
        Instant now = Instant.now();
        Instant expiration = now.plus(10, ChronoUnit.HOURS).atZone(ZoneOffset.UTC).toInstant();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("count", counter);

        return Jwts.builder()
                .setSubject(employeeEmail)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return generateToken(user.getUsername(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()), 0);
    }

    public String generateToken(Employee employee, int counter) {

        Collection<SimpleGrantedAuthority> authorities;
        if (employee.isHasOfficeRights()) {
            authorities = List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"), new SimpleGrantedAuthority("ROLE_OFFICE"));
        } else {
            authorities = List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }

        return generateToken(employee.getEmail(), authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()), counter);
    }

    public String getUserMailFromToken(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public LocalDateTime getExpiration(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration().toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public int getCounter(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return (int) claims.get("count");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
