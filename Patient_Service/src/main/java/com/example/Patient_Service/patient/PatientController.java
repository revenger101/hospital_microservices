package com.example.Patient_Service.patient;

import com.example.Patient_Service.patient.dto.CreatePatientRequest;
import com.example.Patient_Service.patient.dto.PatientResponse;
import com.example.Patient_Service.patient.dto.UpdatePatientRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
	private final PatientService patientService;

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@PostMapping
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CREATED)
	public PatientResponse create(@Valid @RequestBody CreatePatientRequest request) {
		return PatientResponse.from(patientService.create(request));
	}

	@GetMapping("/{id}")
	public PatientResponse get(@PathVariable Long id) {
		return PatientResponse.from(patientService.getById(id));
	}

	@GetMapping
	public List<PatientResponse> list() {
		return patientService.list().stream().map(PatientResponse::from).toList();
	}

	@PutMapping("/{id}")
	public PatientResponse update(@PathVariable Long id, @Valid @RequestBody UpdatePatientRequest request) {
		return PatientResponse.from(patientService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		patientService.delete(id);
	}
}
