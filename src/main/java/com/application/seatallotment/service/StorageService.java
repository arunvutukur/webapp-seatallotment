package com.application.seatallotment.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.SystemOutLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.application.seatallotment.web.rest.errors.DataInconsistencyException;
import com.application.seatallotment.web.rest.errors.SheetNotFoundException;
import com.monitorjbl.xlsx.StreamingReader;
import com.monitorjbl.xlsx.exceptions.MissingSheetException;



@Service
public class StorageService {

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("upload-dir");

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
	
	public void store(MultipartFile file) throws DataInconsistencyException,IOException,MissingSheetException {
		try{
		Workbook workbook= init(file);
		writedataToMongoDb(workbook);
		}catch(FileAlreadyExistsException ex){
			deleteAll();
			Workbook workbook = init(file);
			writedataToMongoDb(workbook);
		}finally{ 
			deleteAll();
			System.gc();
		}
		
	}

	private void writedataToMongoDb(Workbook workbook) throws IOException,SheetNotFoundException {
		try {   
		Sheet UnisysSheet = workbook.getSheet("Unisys Associates");
		UnisysSheet.getLastRowNum();
			   for(Row Associatesrow: UnisysSheet) {
				 EmplId = Associatesrow.getCell(0).getStringCellValue();
			     Name = Associatesrow.getCell(1).getStringCellValue();
				 Manager = Associatesrow.getCell(2).getStringCellValue();
				 Email = Associatesrow.getCell(3).getStringCellValue();
				 Location = Associatesrow.getCell(4).getStringCellValue();
				 Department = Associatesrow.getCell(5).getStringCellValue();
				 SeatNo = Associatesrow.getCell(6).getStringCellValue();
				 SeatNo = dataValidationService.convertToValidSeatNo(SeatNo);
				 isContractor = false;
				//create employee object with contractor= false
				// log.debug("EmplId "+ EmplId+ "Name "+Name+"MANGER "+ Manager+ "Email: "+ Email + "Location: "+ Location+ "department: "+ Department + "Seat: "+ SeatNo + "isCOntractor:" +isContractor );
			}
			Sheet ContractorSheet = workbook.getSheet("Contractors");
			
			for(Row Contractorsrow: ContractorSheet) {
				 EmplId = Contractorsrow.getCell(0).getStringCellValue();
			     Name = Contractorsrow.getCell(1).getStringCellValue();
			     Email = Contractorsrow.getCell(2).getStringCellValue();
				 Department = Contractorsrow.getCell(3).getStringCellValue();
				 Location = Contractorsrow.getCell(4).getStringCellValue();
				 isContractor = true;
				//create  employee object with contractor = true
				// log.debug("EmplId " + EmplId + "Name " + Name + "Email: " + Email + "Location: "
						//+ Location + "department: " + Department + "isCOntractor:" + isContractor);

			}
			Sheet SeatingSheet = workbook.getSheet("Seating");
			log.debug("GOt Seating sheet"+ SeatingSheet.getSheetName());
			for(Row Seatingrow: SeatingSheet) {
				 Floor = Seatingrow.getCell(2).getStringCellValue();
				 DESKNO = Seatingrow.getCell(4).getStringCellValue();
				 Occupancy_Status = Seatingrow.getCell(8).getStringCellValue();
				 log.debug(Seatingrow.getCell(2).getStringCellValue()+ "Floor"+Seatingrow.getCell(8).getStringCellValue() + "Occupancy_Status"+Seatingrow.getCell(4).getStringCellValue() + "Desk");
				//create seat object
			} 		

		}catch(MissingSheetException ex){
			throw new SheetNotFoundException(ex.getMessage());
		}
	}
	
	private Workbook init(MultipartFile file) throws IOException {
		if(!rootLocation.toFile().exists())
			Files.createDirectory(rootLocation);
		  Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		  InputStream is = file.getInputStream();
		  Workbook workbook = StreamingReader.builder().rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
			    .bufferSize(4096) // buffer size to use when reading InputStream to file (defaults to 1024)
				.open(is); // InputStream or File for XLSX file (required)
           return workbook;
	}

	public void deleteAll() {
		if (rootLocation.toFile().exists())
			FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
}