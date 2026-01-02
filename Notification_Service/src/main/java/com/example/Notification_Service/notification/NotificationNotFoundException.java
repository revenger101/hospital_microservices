package com.example.Notification_Service.notification;

public class NotificationNotFoundException extends RuntimeException {
	public NotificationNotFoundException(Long id) {
		super("Notification not found: " + id);
	}
}
