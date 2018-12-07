package com.application.seatallotment.service;

import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ValidationService {
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	//RGA-6F314
    private static final String RGARIT_seat_Pattern = "[RGAIT]{3}-\\d{1,3}F\\d{3}";
    //3F110
	private static final String GOPALAN_Seat_Pattern = "\\dF\\d{1,4}";
	//7S 244
	private static final String DLF_SeatPattern = "\\d{1,3}S\\s\\d{1,4}";
	//Jhelum
	private static final String CONF_ROOM_SeatPattern = "[a-zA-Z]+";

	
  public boolean isValidSeatNumber(String seatNumber) {
	 	if(Pattern.matches(RGARIT_seat_Pattern, seatNumber)|| Pattern.matches(GOPALAN_Seat_Pattern, seatNumber)|| Pattern.matches(DLF_SeatPattern, seatNumber))
			return true;
		return false;
	}
  
	/* 
	If seatNumber pattern matches RGA or RIT or GOPALAN or DLF , then return the same seatNo.
	If seatNo pattern matches conference room pattern, append CONF_ROOM_ to seatNo.
	If it doesnot match any of the above patterns , return "No Seat" 
	*/
  public String convertToValidSeatNo(String seatNumber) {
	  if(Pattern.matches(RGARIT_seat_Pattern, seatNumber) || Pattern.matches(GOPALAN_Seat_Pattern, seatNumber)|| Pattern.matches(DLF_SeatPattern, seatNumber)) {
		 log.debug("Matched Pattern : for Seatnumber"+ seatNumber);
		 return seatNumber;
	 }else if (Pattern.matches(CONF_ROOM_SeatPattern, seatNumber))
		 return "CONF_ROOM_" + seatNumber;
	 return "No Seat";
  } 
  
  
}
