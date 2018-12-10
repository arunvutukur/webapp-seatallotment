package com.application.seatallotment.repository;

import com.application.seatallotment.domain.Access;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Access entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccessRepository extends MongoRepository<Access, String> {

}
