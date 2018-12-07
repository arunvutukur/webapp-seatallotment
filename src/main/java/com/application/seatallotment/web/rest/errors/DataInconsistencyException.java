package com.application.seatallotment.web.rest.errors;

public class DataInconsistencyException extends Exception {

    private static final long serialVersionUID = 1L;

   private String message = "";
    Throwable cause;	
	public DataInconsistencyException(String message, Throwable cause) {
		super();
		this.cause = cause;
		this.message = message;
	}

	public DataInconsistencyException(String message) {
		super();
		this.message = message;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}
}
