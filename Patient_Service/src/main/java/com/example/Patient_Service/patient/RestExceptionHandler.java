package com.example.Patient_Service.patient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(PatientNotFoundException.class)
	public ProblemDetail notFound(PatientNotFoundException ex) {
		ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
		pd.setTitle("Not Found");
		pd.setDetail(ex.getMessage());
		return pd;
	}

	@ExceptionHandler(DuplicateEmailException.class)
	public ProblemDetail conflict(DuplicateEmailException ex) {
		ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
		pd.setTitle("Conflict");
		pd.setDetail(ex.getMessage());
		return pd;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail validation(MethodArgumentNotValidException ex) {
		ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		pd.setTitle("Validation Error");
		pd.setDetail("Request validation failed");
		return pd;
	}
}
