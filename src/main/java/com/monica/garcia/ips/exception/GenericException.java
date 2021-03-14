package com.monica.garcia.ips.exception;


public class GenericException extends Exception {

	private static final long serialVersionUID = 1L;

	public GenericException(String args, Throwable throwable) {
		super(args, throwable);
	}

	public GenericException(Throwable throwable) {
		super(throwable);
	}

	public GenericException(String args) {
		super(args);
	}
	

}
