package com.application.seatallotment.web.rest.errors;

import com.monitorjbl.xlsx.exceptions.MissingSheetException;

public class SheetNotFoundException extends MissingSheetException {

    private static final long serialVersionUID = 1L;

    String message;
    Throwable cause;

    public SheetNotFoundException(String message) {
		super();
		this.message = message;
	}

    public SheetNotFoundException(String message, Throwable cause) {
		super();
		this.message = message;
		this.cause = cause;
	}

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
