package com.example.Appointment_Service.appointment;

import com.example.Appointment_Service.appointment.dto.AppointmentResponse;
import com.example.Appointment_Service.appointment.dto.CreateAppointmentRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
	private final AppointmentService appointmentService;

	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	@PostMapping
	@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CREATED)
	public AppointmentResponse create(@Valid @RequestBody CreateAppointmentRequest request) {
		return AppointmentResponse.from(appointmentService.create(request));
	}

	@GetMapping("/{id}")
	public AppointmentResponse get(@PathVariable Long id) {
		return AppointmentResponse.from(appointmentService.getById(id));
	}

	@GetMapping
	public List<AppointmentResponse> list() {
		return appointmentService.list().stream().map(AppointmentResponse::from).toList();
	}
}
