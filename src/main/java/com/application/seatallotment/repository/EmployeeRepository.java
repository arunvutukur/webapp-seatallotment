package com.application.seatallotment.repository;

import java.nio.file.Paths;
import java.util.List;
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
   Optional<Employee> findByempId(String id);
	void deleteByempId(String id);
	List<Employee> findBymanager(String string);
}
