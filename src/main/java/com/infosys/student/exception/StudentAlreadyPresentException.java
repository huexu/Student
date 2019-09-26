package com.infosys.student.exception;

@SuppressWarnings("serial")
public class StudentAlreadyPresentException extends Exception {
	public StudentAlreadyPresentException(String message) {
		super(message);
	}
}
