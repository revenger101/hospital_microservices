package com.example.Notification_Service.notification.dto;

import com.example.Notification_Service.notification.Notification;

import java.time.Instant;

public class NotificationResponse {
	private Long id;
	private String recipient;
	private String message;
	private Instant createdAt;

	public static NotificationResponse from(Notification n) {
		NotificationResponse r = new NotificationResponse();
		r.id = n.getId();
		r.recipient = n.getRecipient();
		r.message = n.getMessage();
		r.createdAt = n.getCreatedAt();
		return r;
	}

	public Long getId() {
		return id;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getMessage() {
		return message;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
