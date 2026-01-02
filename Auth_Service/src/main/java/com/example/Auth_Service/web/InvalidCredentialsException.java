package main.java.com.example.Auth_Service.web;

public class InvalidCredentialsException extends RuntimeException {
	public InvalidCredentialsException() {
		super("Invalid username or password");
	}
}
