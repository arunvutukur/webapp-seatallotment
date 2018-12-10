package com.application.seatallotment.web.rest;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import com.application.seatallotment.service.StorageService;
import com.application.seatallotment.service.ValidationService;
import com.application.seatallotment.web.rest.errors.DataInconsistencyException;
import com.application.seatallotment.web.rest.errors.FileNotSupportedException;
import com.application.seatallotment.web.rest.errors.SheetNotFoundException;
import com.monitorjbl.xlsx.exceptions.MissingSheetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UploadController {

	@Autowired
	StorageService storageService;

	@Autowired
	ValidationService dataService;
	List<String> files = new ArrayList<String>();

	@PostMapping("/post")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		String message = "";

		try {
			// 1. check type of file : if not xlsx throw exception
			if (!(file.getOriginalFilename().endsWith(".xlsx"))) {
				throw new FileNotSupportedException(file.getOriginalFilename());
			}
			storageService.store(file);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (FileNotSupportedException ex) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
		} catch (DataInconsistencyException ex) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
		} catch(SheetNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
		} catch (IOException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}

	}

}
