package com.application.seatallotment.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.application.seatallotment.domain.Employee;
import com.application.seatallotment.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class StorageService {

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
    //private final Path rootLocation = Paths.get("upload-dir");
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public StorageService(EmployeeRepository employeeRepository){
	   this.employeeRepository=employeeRepository;
	}
	public void store(MultipartFile file) {
		try {
			//Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
			writedataToMongoDb(file);
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	private void writedataToMongoDb(MultipartFile file) throws IOException {
		InputStream is = file.getInputStream();
		log.debug("Got input Stream from uploaded file");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
		Pattern pattern = Pattern.compile(",");
        List <Employee> employees = br.lines().skip(1).map(line -> {
		String[] row = pattern.split(line);
		String name=(String) row[0];
		String empId=(String) row[1];
		String manager= (String) row[2];
		String email= (String) row[3];
		String location= (String) (row[4]);
		String department = (String) (row[5]);
        return new Employee(name, empId, manager , email, location,department);
       }).collect(Collectors.toList());
        ListIterator<Employee> listIterator=employees.listIterator();
		while(listIterator.hasNext()){
			if(employeeRepository.existsByempId(listIterator.next().getEmpId()))
			listIterator.remove();
			log.debug("Employee in list iterator loop:" );
		}
	    employeeRepository.saveAll(employees);
	   		} catch (Exception ex) {
           log.debug("exception in storage service :"+ ex.getMessage());
		}

	}
}
