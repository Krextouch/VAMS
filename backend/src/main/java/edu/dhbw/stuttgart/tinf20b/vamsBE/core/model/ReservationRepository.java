package edu.dhbw.stuttgart.tinf20b.vamsBE.core.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    Reservation findById(int id);
}
