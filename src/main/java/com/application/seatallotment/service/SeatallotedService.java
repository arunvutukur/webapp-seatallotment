package com.application.seatallotment.service;
import com.application.seatallotment.domain.Seatalloted;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Seatalloted.
 */
public interface SeatallotedService {
    
    Seatalloted save(Seatalloted seatalloted);

    Seatalloted update(Seatalloted seatalloted);

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Seatalloted> findAll(Pageable pageable);

    /**
     * Get the "id" seat.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Seatalloted> findOne(String id);

    /**
     * Delete the "id" seat.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
