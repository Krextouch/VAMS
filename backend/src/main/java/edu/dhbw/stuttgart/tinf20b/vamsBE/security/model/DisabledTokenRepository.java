package edu.dhbw.stuttgart.tinf20b.vamsBE.security.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DisabledTokenRepository extends CrudRepository<DisabledToken, String> {
    Optional<DisabledToken> findByToken(String token);
    Optional<List<DisabledToken>> findByExpiresBefore(LocalDateTime time);
}
