package com.example.Patient_Service.patient.dto;

import com.example.Patient_Service.patient.Patient;

import java.time.LocalDate;

public class PatientResponse {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private LocalDate dateOfBirth;

	public static PatientResponse from(Patient patient) {
		PatientResponse r = new PatientResponse();
		r.id = patient.getId();
		r.firstName = patient.getFirstName();
		r.lastName = patient.getLastName();
		r.email = patient.getEmail();
		r.phone = patient.getPhone();
		r.dateOfBirth = patient.getDateOfBirth();
		return r;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
}
