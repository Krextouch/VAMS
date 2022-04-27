package edu.dhbw.stuttgart.tinf20b.vamsBE.raspi;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.Reservation;
import edu.dhbw.stuttgart.tinf20b.vamsBE.core.model.ReservationRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.Employee;
import edu.dhbw.stuttgart.tinf20b.vamsBE.employeePortal.model.EmployeeRepository;
import edu.dhbw.stuttgart.tinf20b.vamsBE.raspi.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RaspiService {

    private final DeviceRepository deviceRepository;
    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;
    private final RaspiLogRepository raspiLogRepository;

    @Value("${app.raspiLogSavingDays}")
    private int LOG_SAVING_DAYS;

    @Autowired
    public RaspiService(DeviceRepository deviceRepository,
                        EmployeeRepository employeeRepository,
                        ReservationRepository reservationRepository,
                        RaspiLogRepository raspiLogRepository) {
        this.deviceRepository = deviceRepository;
        this.employeeRepository = employeeRepository;
        this.reservationRepository = reservationRepository;
        this.raspiLogRepository = raspiLogRepository;
    }

    public SummonResponse summon(SummonRequest summonRequest) {
        boolean isOfficeWorker = false;

        Optional<Device> device = deviceRepository.findByDeviceId(summonRequest.getDeviceId());
        if (device.isEmpty() || !BCrypt.checkpw(summonRequest.getToken(), device.get().getToken())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Employee> employee = employeeRepository.findByWorkCard(summonRequest.getWorkCard());
        if (employee.isEmpty()) {
            addRaspiLog(summonRequest.getWorkCard(), null, device.get(), false, false);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if(employee.get().isHasOfficeRights()) {
            isOfficeWorker = true;
        }

        Optional<Reservation> reservationList = reservationRepository.findByEmployeeAndEndTimeOfReservationAfterAndStartTimeOfReservationBeforeAndVehicle(employee.get(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                device.get().getVin());

        if (reservationList.isEmpty() && !isOfficeWorker) {
            addRaspiLog(summonRequest.getWorkCard(), employee.get(), device.get(), false, false);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No reservations found");
        }

        //Exception: All office workers can open the cars
        if (reservationList.isEmpty() && isOfficeWorker) {
            addRaspiLog(summonRequest.getWorkCard(), employee.get(), device.get(), true, employee.get().isHasDrivingLicense());
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

        //only normal employees need a verified reservation to drive a car
        if (!reservationList.get().getIsVerified() && !isOfficeWorker) {
            addRaspiLog(summonRequest.getWorkCard(), employee.get(), device.get(), false, false);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Reservation not validated");
        }

        addRaspiLog(summonRequest.getWorkCard(), employee.get(), device.get(), true, employee.get().isHasDrivingLicense());
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

    private void addRaspiLog(String workCard, Employee employee, Device device, boolean authorizedOpening, boolean authorizedDriving) {
        RaspiLog raspiLog = RaspiLog.builder()
                .device(device)
                .workCard(workCard)
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .employee(employee)
                .authorizedOpening(authorizedOpening)
                .authorizedDriving(authorizedDriving)
                .build();
        raspiLogRepository.save(raspiLog);
    }

    @Scheduled(cron = "0 02 0 * * *") //every day at 00:02
    private void deleteExpiredRaspiLogs() {
        log.info("SCHEDULED: deleteExpiredRaspiLogs");
        Optional<List<RaspiLog>> raspiLogs = raspiLogRepository.findByTimestampIsBefore(LocalDateTime.now(ZoneOffset.UTC).minusDays(LOG_SAVING_DAYS));
        raspiLogs.ifPresent(raspiLogRepository::deleteAll);
    }
}
