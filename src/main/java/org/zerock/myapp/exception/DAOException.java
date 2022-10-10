package org.zerock.myapp.exception;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	public DAOException(String message) {
		super(message);
	} // Constructor
	
	public DAOException(Exception e) {
		super(e);
	} // Constructor
	
} // end class
