package com.example.Patient_Service.patient;

public class PatientNotFoundException extends RuntimeException {
	public PatientNotFoundException(Long id) {
		super("Patient not found: " + id);
	}
}
