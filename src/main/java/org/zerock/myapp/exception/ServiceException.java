package org.zerock.myapp.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ServiceException (String message) {
		super(message);
	} // Constructor
	
	public ServiceException (Exception e) {
		super(e);
	} // Constructor

} // end class
