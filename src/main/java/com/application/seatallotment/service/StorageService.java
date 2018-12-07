package com.application.seatallotment.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.application.seatallotment.web.rest.errors.DataInconsistencyException;
import com.monitorjbl.xlsx.StreamingReader;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class StorageService {

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("upload-dir");

	private static String UNISYS_ASSOCIATE_SHEET = "";
	private static String CONTRACTOR_SHEET = "Contractors";
	private static String SEATING_SHEET = "Seating";
	private String EmplId = "";
	private String Name = "";
	private String Manager = "";
	private String Email = "";
	private String Location = "";
	private String Department = "";
	private String SeatNo = "";
	private String Occupancy_Status = "";
	private boolean isContractor = false;
	private String AssignedLocation = "";
	private String DESKNO = "";
	
	private String Floor = "";
	
	@Autowired
	private ValidationService dataValidationService;
	
	public void store(MultipartFile file) throws DataInconsistencyException,IOException {
		init();
	    writedataToMongoDb(file);
		deleteAll();
		
	}

	

	private void writedataToMongoDb(MultipartFile file) throws IOException {
		
		    Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
			InputStream is = file.getInputStream();
	        Workbook workbook = StreamingReader.builder()
				          .rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
				          .bufferSize(4096)  // buffer size to use when reading InputStream to file (defaults to 1024)
						  .open(is);         // InputStream or File for XLSX file (required)
							  
            Sheet UnisysSheet = workbook.getSheet("Unisys Associates");
			for(Row row: UnisysSheet) {
				 EmplId = row.getCell(0).getStringCellValue();
			     Name = row.getCell(1).getStringCellValue();
				 Manager = row.getCell(2).getStringCellValue();
				 Email = row.getCell(3).getStringCellValue();
				 Location = row.getCell(4).getStringCellValue();
				 Department = row.getCell(5).getStringCellValue();
				 SeatNo = row.getCell(6).getStringCellValue();
				 SeatNo = dataValidationService.convertToValidSeatNo(SeatNo);
				 isContractor = false;
				//create employee object with contractor= false
			}
			Sheet ContractorSheet = workbook.getSheet("Contractors");
			
			for(Row row: ContractorSheet) {
				 EmplId = row.getCell(0).getStringCellValue();
			     Name = row.getCell(1).getStringCellValue();
			     Email = row.getCell(2).getStringCellValue();
				 Department = row.getCell(3).getStringCellValue();
				 Location = row.getCell(4).getStringCellValue();
				 isContractor = true;
				//create  employee object with contractor = true
			}
			for(Row row: workbook.getSheet("Seating")) {
				 Floor = row.getCell(3).getStringCellValue();
				 DESKNO = row.getCell(4).getStringCellValue();
				 Occupancy_Status = row.getCell(8).getStringCellValue();
				//create seat object
			} 		
			
	}
	
	private void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}

	public void deleteAll() {
		if (rootLocation.toFile().exists())
			FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
}