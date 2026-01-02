package main.java.com.example.Auth_Service.web;

public class LoginResponse {
	private final String tokenType = "Bearer";
	private final String accessToken;
	private final String expiresAt;

	public LoginResponse(String accessToken, String expiresAt) {
		this.accessToken = accessToken;
		this.expiresAt = expiresAt;
	}

	public String getTokenType() {
		return tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getExpiresAt() {
		return expiresAt;
	}
}
