package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.security.model.ResponseLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserAuthorizationService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserAuthorizationService(EmployeeRepository employeeRepository,
                                    JwtTokenProvider jwtTokenProvider) {
        this.employeeRepository = employeeRepository;
        this.jwtTokenProvider = jwtTokenProvider;
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
        }
        else {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
