package com.rootlets.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class StudentCourseIllegalStateException extends IllegalStateException {
	private static final long serialVersionUID = 1L;
	
	public StudentCourseIllegalStateException(String message) {
		super(message);
	}
	
	public StudentCourseIllegalStateException(Throwable e) {
		super(e);
	}

}
