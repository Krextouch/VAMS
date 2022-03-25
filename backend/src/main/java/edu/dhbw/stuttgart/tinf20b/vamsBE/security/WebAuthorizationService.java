package edu.dhbw.stuttgart.tinf20b.vamsBE.security;

import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class WebAuthorizationService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public WebAuthorizationService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
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

            authorities = List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        } else {
            authorities = List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"), new SimpleGrantedAuthority("ROLE_OFFICE"));
        }

        return new User(String.valueOf(employee.getEmail()), employee.getPassword(), authorities);
    }

//        if (BCrypt.checkpw(password, employee.getPassword())) {
//
//        } else {
//            throw new BadCredentialsException("Wrong password");
//        }
//
//    }

}
