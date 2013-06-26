package com.example.salesandprices;

public class DataFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7266151058247927954L;

	public DataFileException() {
		super();
	}

	public DataFileException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public DataFileException(String detailMessage) {
		super(detailMessage);
	}

	public DataFileException(Throwable throwable) {
		super(throwable);
	}
	
}
