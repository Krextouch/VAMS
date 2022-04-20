package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.ReservationRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.Device;
import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.DeviceRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.SummonRequest;
import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.SummonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class RaspiService {

    private final DeviceRepository deviceRepository;
    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public RaspiService(DeviceRepository deviceRepository,
                        EmployeeRepository employeeRepository,
                        ReservationRepository reservationRepository) {
        this.deviceRepository = deviceRepository;
        this.employeeRepository = employeeRepository;
        this.reservationRepository = reservationRepository;
    }

    public SummonResponse summon(SummonRequest summonRequest) {
        Optional<Device> device = deviceRepository.findByDeviceId(summonRequest.getDeviceId());
        if (device.isEmpty() || !BCrypt.checkpw(summonRequest.getToken(), device.get().getToken())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Employee> employee = employeeRepository.findByWorkCard(summonRequest.getWorkCard());
        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        //Exception: All office workers can open the cars
        if (employee.get().isHasOfficeRights()) {
            return SummonResponse.builder()
                    .authorizedOpening(true)
                    .authorizedDriving(employee.get().isHasDrivingLicense())
                    .email(employee.get().getEmail())
                    .firstName(employee.get().getFirstName())
                    .lastName(employee.get().getLastName())
                    .beginn(LocalDateTime.now(ZoneOffset.UTC))
                    .end(LocalDateTime.of(0, 1, 1, 0, 0, 0, 0))
                    .build();
        }

        Optional<Reservation> reservationList = reservationRepository.findByEmployeeAndEndTimeOfReservationAfterAndStartTimeOfReservationBeforeAndVehicle(employee.get(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                device.get().getVin());

        if (reservationList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No reservations found");
        }

        if (!reservationList.get().getIsVerified()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Reservation not validated");
        }

        return SummonResponse.builder()
                .authorizedOpening(true)
                .authorizedDriving(employee.get().isHasDrivingLicense())
                .email(employee.get().getEmail())
                .firstName(employee.get().getFirstName())
                .lastName(employee.get().getLastName())
                .beginn(reservationList.get().getStartTimeOfReservation())
                .end(reservationList.get().getEndTimeOfReservation())
                .build();
    }
}
