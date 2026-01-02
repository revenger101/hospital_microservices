package com.example.Doctor_Service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "notification-service")
public interface NotificationClient {
	@PostMapping("/demo/notification/send")
	Map<String, Object> send(@RequestParam("to") String to, @RequestParam("message") String message);
}
