package com.example.Doctor_Service.doctor.dto;

import com.example.Doctor_Service.doctor.Doctor;

public class DoctorResponse {
	private Long id;
	private String name;
	private String specialty;
	private boolean active;

	public static DoctorResponse from(Doctor doctor) {
		DoctorResponse r = new DoctorResponse();
		r.id = doctor.getId();
		r.name = doctor.getName();
		r.specialty = doctor.getSpecialty();
		r.active = doctor.isActive();
		return r;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSpecialty() {
		return specialty;
	}

	public boolean isActive() {
		return active;
	}
}
