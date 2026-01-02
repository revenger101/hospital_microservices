package com.example.Notification_Service.notification;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class NotificationService {
	private final NotificationRepository notificationRepository;

	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Transactional
	public Notification create(String recipient, String message) {
		Notification notification = new Notification(recipient, message, Instant.now());
		return notificationRepository.save(notification);
	}

	@Transactional(readOnly = true)
	public Notification getById(Long id) {
		return notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public List<Notification> list() {
		return notificationRepository.findAll();
	}
}
