package com.mycompany.myapp.web.rest.errors;

public class FileNotSupportedException extends Exception {

    private static final long serialVersionUID = 1L;

    String message;
    Throwable cause;

    public FileNotSupportedException(String message) {
		super();
		this.message = message;
	}

    public FileNotSupportedException(String message, Throwable cause) {
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
