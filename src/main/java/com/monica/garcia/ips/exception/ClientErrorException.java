package com.monica.garcia.ips.exception;

import org.springframework.http.HttpStatus;

public class ClientErrorException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6344553059336155056L;
	
	private final HttpStatus statusCode; 
	
	public ClientErrorException(String message) {
		super(message);
		this.statusCode = null;
	}
	
	public ClientErrorException(String message, HttpStatus statusCode) {
		super( message);
		this.statusCode = statusCode;

	}
	
	public ClientErrorException(String message, HttpStatus statusCode, Throwable cause) {
		super( message, cause );
		this.statusCode = statusCode;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}
	
}
