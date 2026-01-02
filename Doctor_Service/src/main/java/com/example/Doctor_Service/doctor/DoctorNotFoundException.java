package com.example.Doctor_Service.doctor;

public class DoctorNotFoundException extends RuntimeException {
	public DoctorNotFoundException(Long id) {
		super("Doctor not found: " + id);
	}
}
