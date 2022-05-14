package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.RaspiLog;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.DisabledToken;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.DisabledTokenRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.ResponseLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserAuthorizationService implements UserDetailsService {

    @Value("${app.maxRenewCount}")
    private int maxRenewCount;

    private final EmployeeRepository employeeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final DisabledTokenRepository disabledTokenRepository;

    @Autowired
    public UserAuthorizationService(EmployeeRepository employeeRepository,
                                    JwtTokenProvider jwtTokenProvider,
                                    DisabledTokenRepository disabledTokenRepository) {
        this.employeeRepository = employeeRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.disabledTokenRepository = disabledTokenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
        Search Employee with username. Username could be Email, Employee_id or Name_Tag
         */
        Employee employee;
        if (username.contains("@")) {
            employee = employeeRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        } else if (username.matches("^[0-9]*$")) {
            employee = employeeRepository.findByEmployeeId(Integer.parseInt(username)).orElseThrow(() -> new UsernameNotFoundException("Employee id not found"));
        } else {
            employee = employeeRepository.findByNameTag(username).orElseThrow(() -> new UsernameNotFoundException("Name tag not found"));
        }

        /*
        Set User Roles for Spring Security
         */
        Collection<SimpleGrantedAuthority> authorities;
        if (employee.isHasOfficeRights()) {
            authorities = List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"), new SimpleGrantedAuthority("ROLE_OFFICE"));
        } else {
            authorities = List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }

        return new User(String.valueOf(employee.getEmail()), employee.getPassword(), authorities);
    }

    public ResponseLogin getUserDetails(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Optional<Employee> employee = employeeRepository.findByEmail(user.getUsername());
        String token = this.jwtTokenProvider.generateToken(authentication);
        if (employee.isPresent()) {
            return ResponseLogin.builder()
                    .token(token)
                    .expiration(this.jwtTokenProvider.getExpiration(token))
                    .email(employee.get().getEmail())
                    .nameTag(employee.get().getNameTag())
                    .firstName(employee.get().getFirstName())
                    .lastName(employee.get().getLastName())
                    .hasDrivingLicense(employee.get().isHasDrivingLicense())
                    .hasOfficeRights(employee.get().isHasOfficeRights())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public void disableToken(String bearer) {
        String token = bearer.substring(7);
        this.disabledTokenRepository.save(DisabledToken.builder()
                .token(token)
                .expires(this.jwtTokenProvider.getExpiration(token))
                .build());
    }

    public ResponseLogin renewToken(String bearer) {
        String oldToken = bearer.substring(7);
        Optional<Employee> employee = employeeRepository.findByEmail(jwtTokenProvider.getUserMailFromToken(oldToken));
        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.GONE);
        }
        int counter = jwtTokenProvider.getCounter(oldToken);
        //max token renewal exceeded
        if (counter >= maxRenewCount) {
            throw new ResponseStatusException(HttpStatus.LOCKED);
        }
        String newToken = jwtTokenProvider.generateToken(employee.get(), ++counter);

        ResponseLogin login = ResponseLogin.builder()
                .token(newToken)
                .expiration(this.jwtTokenProvider.getExpiration(newToken))
                .email(employee.get().getEmail())
                .nameTag(employee.get().getNameTag())
                .firstName(employee.get().getFirstName())
                .lastName(employee.get().getLastName())
                .hasDrivingLicense(employee.get().isHasDrivingLicense())
                .hasOfficeRights(employee.get().isHasOfficeRights())
                .build();

        disableToken(bearer);

        return login;
    }

    @Scheduled(cron = "0 0 */1 * * *")
    public void deleteExpiredDisabledTokens() {
        log.info("SCHEDULED: deleteExpiredDisabledTokens");
        Optional<List<DisabledToken>> disabledTokens = disabledTokenRepository.findByExpiresBefore(LocalDateTime.now(ZoneOffset.UTC));
        disabledTokens.ifPresent(disabledTokenRepository::deleteAll);
    }
}
