package com.example.Doctor_Service.demo;

import com.example.Doctor_Service.client.NotificationClient;
import com.example.Doctor_Service.doctor.Doctor;
import com.example.Doctor_Service.doctor.DoctorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/demo/doctor")
public class DoctorDemoController {
	private final DoctorService doctorService;
	private final NotificationClient notificationClient;

	public DoctorDemoController(DoctorService doctorService, NotificationClient notificationClient) {
		this.doctorService = doctorService;
		this.notificationClient = notificationClient;
	}

	@GetMapping("/check")
	public Map<String, Object> check(@RequestParam("doctorId") Long doctorId, @RequestParam("patientId") Long patientId) {
		Doctor doctor = doctorService.getById(doctorId);
		String status = doctor.isActive() ? "OK" : "NOT_AVAILABLE";

		Map<String, Object> notification = notificationClient.send(
				"patient-" + patientId,
				"Doctor " + doctorId + " (" + doctor.getName() + ") checked appointment for patient " + patientId + ". Status=" + status
		);

		return Map.of(
				"doctorId", doctorId,
				"patientId", patientId,
				"status", status,
				"notification", notification
		);
	}
}
