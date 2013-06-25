package com.example.salesandprices;

public class DataFileException extends Exception {

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
