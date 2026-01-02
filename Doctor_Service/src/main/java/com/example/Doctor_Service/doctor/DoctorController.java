package com.example.Doctor_Service.doctor;

import com.example.Doctor_Service.doctor.dto.CreateDoctorRequest;
import com.example.Doctor_Service.doctor.dto.DoctorResponse;
import com.example.Doctor_Service.doctor.dto.UpdateDoctorRequest;
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
@RequestMapping("/doctors")
public class DoctorController {
	private final DoctorService doctorService;

	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}

	@PostMapping
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CREATED)
	public DoctorResponse create(@Valid @RequestBody CreateDoctorRequest request) {
		return DoctorResponse.from(doctorService.create(request));
	}

	@GetMapping("/{id}")
	public DoctorResponse get(@PathVariable Long id) {
		return DoctorResponse.from(doctorService.getById(id));
	}

	@GetMapping
	public List<DoctorResponse> list() {
		return doctorService.list().stream().map(DoctorResponse::from).toList();
	}

	@PutMapping("/{id}")
	public DoctorResponse update(@PathVariable Long id, @Valid @RequestBody UpdateDoctorRequest request) {
		return DoctorResponse.from(doctorService.update(id, request));
	}

	@DeleteMapping("/{id}")
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		doctorService.delete(id);
	}
}
