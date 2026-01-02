package com.example.Appointment_Service.appointment;

import com.example.Appointment_Service.appointment.dto.CreateAppointmentRequest;
import com.example.Appointment_Service.client.DoctorClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {
	private final AppointmentRepository appointmentRepository;
	private final DoctorClient doctorClient;

	public AppointmentService(AppointmentRepository appointmentRepository, DoctorClient doctorClient) {
		this.appointmentRepository = appointmentRepository;
		this.doctorClient = doctorClient;
	}

	@Transactional
	public Appointment create(CreateAppointmentRequest request) {
		Map<String, Object> doctorCheck = doctorClient.check(request.getDoctorId(), request.getPatientId());
		String status = String.valueOf(doctorCheck.getOrDefault("status", "OK"));

		Appointment appointment = new Appointment(
				request.getPatientId(),
				request.getDoctorId(),
				status,
				Instant.now()
		);
		return appointmentRepository.save(appointment);
	}

	@Transactional(readOnly = true)
	public Appointment getById(Long id) {
		return appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public List<Appointment> list() {
		return appointmentRepository.findAll();
	}
}
