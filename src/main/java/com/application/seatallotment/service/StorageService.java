package com.application.seatallotment.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.application.seatallotment.domain.Employee;
import com.application.seatallotment.repository.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class StorageService {

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("upload-dir");

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public void store(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
			writedataToMongoDb(file);
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	
	private void writedataToMongoDb(MultipartFile file) throws IOException {
		List<Object[]> result = new ArrayList<>();
		InputStream is = file.getInputStream();
		String cvsSplitBy = ",";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			while ((line = br.readLine()) != null) {
				Object[] row = (Object[])line.split(cvsSplitBy);
				result.add(row);
			}
			for(Object[] value : result) {
				System.out.println("value"+ value);
				Employee employee=new Employee(value);
				System.out.println("values from User Object: "+(String)value[0]+" "+(String)value[1]);
				employeeRepository.save(employee);
				System.out.println(employeeRepository.findBymanager((String) value[6]).get(0));
			}
		} catch (Exception ex) {

		}

	}

	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}
