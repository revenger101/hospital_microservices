package com.example.Notification_Service.demo;

import com.example.Notification_Service.notification.Notification;
import com.example.Notification_Service.notification.NotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/demo/notification")
public class NotificationDemoController {
	private final NotificationService notificationService;

	public NotificationDemoController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@PostMapping("/send")
	public Map<String, Object> send(@RequestParam("to") String to, @RequestParam("message") String message) {
		Notification saved = notificationService.create(to, message);
		return Map.of(
				"sent", true,
				"id", saved.getId(),
				"to", to,
				"message", message,
				"timestamp", saved.getCreatedAt().toString()
		);
	}
}
