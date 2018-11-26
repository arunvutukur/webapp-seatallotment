package com.application.seatallotment.service.impl;

import com.application.seatallotment.service.EmployeeService;
import com.application.seatallotment.domain.Employee;
import com.application.seatallotment.repository.EmployeeRepository;
import com.application.seatallotment.service.dto.EmployeeDTO;
import com.application.seatallotment.service.mapper.EmployeeMapper;
import com.application.seatallotment.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
/**
 * Service Implementation for managing Employee.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        if(employeeRepository.existsByempId(employee.getEmpId()))
        throw new BadRequestAlertException("A new employee cannot already have an existing empID", employee.getEmpId(), ": Try deleting and adding again!");
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable)
            .map(employeeMapper::toDto);
    }


    /**
     * Get one employee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<EmployeeDTO> findOne(String id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id)
            .map(employeeMapper::toDto);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) {
        log.debug("Request to update Employee : {}", employeeDTO);
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeDTO.getId());
        employeeOptional.get().setDepartment(employeeDTO.getDepartment());
        employeeOptional.get().setEmail(employeeDTO.getEmail());
        employeeOptional.get().setName(employeeDTO.getName());
        employeeOptional.get().setEmpId(employeeDTO.getEmpId());
        employeeOptional.get().setManager(employeeDTO.getManager());
        employeeOptional.get().setLocation(employeeDTO.getLocation());
        employeeOptional.get().setPendingForApproval(false);
        employeeOptional.get().setRequestForApproval(false);
        Employee employee = employeeRepository.save(employeeOptional.get());
        return employeeMapper.toDto(employee);
    }
}
