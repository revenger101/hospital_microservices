package com.example.Appointment_Service.appointment.dto;

import com.example.Appointment_Service.appointment.Appointment;

import java.time.Instant;

public class AppointmentResponse {
	private Long id;
	private Long patientId;
	private Long doctorId;
	private String status;
	private Instant createdAt;

	public static AppointmentResponse from(Appointment a) {
		AppointmentResponse r = new AppointmentResponse();
		r.id = a.getId();
		r.patientId = a.getPatientId();
		r.doctorId = a.getDoctorId();
		r.status = a.getStatus();
		r.createdAt = a.getCreatedAt();
		return r;
	}

	public Long getId() {
		return id;
	}

	public Long getPatientId() {
		return patientId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public String getStatus() {
		return status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
