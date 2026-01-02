package com.example.Patient_Service.demo;

import com.example.Patient_Service.client.AppointmentClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/demo/patient")
public class PatientDemoController {
	private final AppointmentClient appointmentClient;

	public PatientDemoController(AppointmentClient appointmentClient) {
		this.appointmentClient = appointmentClient;
	}

	@GetMapping("/book")
	public Map<String, Object> book(@RequestParam("patientId") Long patientId, @RequestParam("doctorId") Long doctorId) {
		Map<String, Object> appointment = appointmentClient.create(patientId, doctorId);
		return Map.of(
				"patientId", patientId,
				"doctorId", doctorId,
				"appointment", appointment
		);
	}
}
