package com.example.Appointment_Service.appointment.dto;

import jakarta.validation.constraints.NotNull;

public class CreateAppointmentRequest {
	@NotNull
	private Long patientId;

	@NotNull
	private Long doctorId;

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
}
