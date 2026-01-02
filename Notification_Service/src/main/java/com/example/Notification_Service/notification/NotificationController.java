package com.example.Notification_Service.notification;

import com.example.Notification_Service.notification.dto.CreateNotificationRequest;
import com.example.Notification_Service.notification.dto.NotificationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@PostMapping
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CREATED)
	public NotificationResponse create(@Valid @RequestBody CreateNotificationRequest request) {
		return NotificationResponse.from(notificationService.create(request.getRecipient(), request.getMessage()));
	}

	@GetMapping("/{id}")
	public NotificationResponse get(@PathVariable Long id) {
		return NotificationResponse.from(notificationService.getById(id));
	}

	@GetMapping
	public List<NotificationResponse> list() {
		return notificationService.list().stream().map(NotificationResponse::from).toList();
	}
}
