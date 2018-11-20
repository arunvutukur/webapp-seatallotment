package com.application.seatallotment.repository;

import java.util.Optional;

import com.application.seatallotment.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

	Optional<Employee> findByempId(String empId);

	boolean existsByempId(String empId);

}
