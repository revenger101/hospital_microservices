package main.java.com.example.Auth_Service.web;

import main.java.com.example.Auth_Service.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	private final JwtService jwtService;
	private final String allowedUsername;
	private final String allowedPassword;

	public AuthController(
			JwtService jwtService,
			@Value("${app.auth.username:admin}") String allowedUsername,
			@Value("${app.auth.password:admin123}") String allowedPassword
	) {
		this.jwtService = jwtService;
		this.allowedUsername = allowedUsername;
		this.allowedPassword = allowedPassword;
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public LoginResponse login(@Valid @RequestBody LoginRequest request) {
		if (!allowedUsername.equals(request.getUsername()) || !allowedPassword.equals(request.getPassword())) {
			throw new InvalidCredentialsException();
		}

		String token = jwtService.issueToken(request.getUsername());
		return new LoginResponse(token, jwtService.expiresAt().toString());
	}
}
