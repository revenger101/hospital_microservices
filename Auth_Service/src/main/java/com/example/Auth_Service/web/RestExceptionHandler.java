package main.java.com.example.Auth_Service.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(InvalidCredentialsException.class)
	public ProblemDetail invalid(InvalidCredentialsException ex) {
		ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
		pd.setTitle("Unauthorized");
		pd.setDetail(ex.getMessage());
		return pd;
	}
}
