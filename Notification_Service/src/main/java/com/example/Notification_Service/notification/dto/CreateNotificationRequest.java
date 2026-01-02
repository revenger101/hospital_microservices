package com.example.Notification_Service.notification.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateNotificationRequest {
	@NotBlank
	private String recipient;

	@NotBlank
	private String message;

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
