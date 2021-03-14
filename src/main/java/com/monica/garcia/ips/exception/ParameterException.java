package com.monica.garcia.ips.exception;

public class ParameterException extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ParameterException(Throwable cause) {
		super(cause);
	}

	public ParameterException(String e) {
		super(e);
	}
}
