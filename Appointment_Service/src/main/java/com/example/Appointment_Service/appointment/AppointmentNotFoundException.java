package com.example.Appointment_Service.appointment;

public class AppointmentNotFoundException extends RuntimeException {
	public AppointmentNotFoundException(Long id) {
		super("Appointment not found: " + id);
	}
}
