package com.application.seatallotment.repository;

import com.application.seatallotment.domain.Seatalloted;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Seatalloted entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeatallotedRepository extends MongoRepository<Seatalloted, String> {

}
