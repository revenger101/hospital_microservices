package com.example.Doctor_Service.doctor.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateDoctorRequest {
	@NotBlank
	private String name;

	@NotBlank
	private String specialty;

	private boolean active = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
