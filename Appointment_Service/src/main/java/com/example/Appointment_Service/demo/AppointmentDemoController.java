package com.example.Appointment_Service.demo;

import com.example.Appointment_Service.client.DoctorClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import com.example.Appointment_Service.appointment.Appointment;
import com.example.Appointment_Service.appointment.AppointmentService;
import com.example.Appointment_Service.appointment.dto.CreateAppointmentRequest;

@RestController
@RequestMapping("/demo/appointment")
public class AppointmentDemoController {
	private final AppointmentService appointmentService;
	private final DoctorClient doctorClient;

	public AppointmentDemoController(AppointmentService appointmentService, DoctorClient doctorClient) {
		this.appointmentService = appointmentService;
		this.doctorClient = doctorClient;
	}

	@GetMapping("/create")
	public Map<String, Object> create(@RequestParam("patientId") Long patientId, @RequestParam("doctorId") Long doctorId) {
		Map<String, Object> doctorCheck = doctorClient.check(doctorId, patientId);

		CreateAppointmentRequest req = new CreateAppointmentRequest();
		req.setPatientId(patientId);
		req.setDoctorId(doctorId);
		Appointment saved = appointmentService.create(req);

		return Map.of(
				"appointmentCreated", true,
				"appointmentId", saved.getId(),
				"status", saved.getStatus(),
				"createdAt", saved.getCreatedAt().toString(),
				"patientId", patientId,
				"doctorId", doctorId,
				"doctorCheck", doctorCheck
		);
	}
}
