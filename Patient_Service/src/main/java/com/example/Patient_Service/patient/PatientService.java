package com.example.Patient_Service.patient;

import com.example.Patient_Service.patient.dto.CreatePatientRequest;
import com.example.Patient_Service.patient.dto.UpdatePatientRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {
	private final PatientRepository patientRepository;

	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Transactional
	public Patient create(CreatePatientRequest request) {
		if (patientRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateEmailException(request.getEmail());
		}

		Patient patient = new Patient(
				request.getFirstName(),
				request.getLastName(),
				request.getEmail(),
				request.getPhone(),
				request.getDateOfBirth()
		);
		return patientRepository.save(patient);
	}

	@Transactional(readOnly = true)
	public Patient getById(Long id) {
		return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public List<Patient> list() {
		return patientRepository.findAll();
	}

	@Transactional
	public Patient update(Long id, UpdatePatientRequest request) {
		Patient patient = getById(id);

		if (request.getFirstName() != null) {
			patient.setFirstName(request.getFirstName());
		}
		if (request.getLastName() != null) {
			patient.setLastName(request.getLastName());
		}
		if (request.getEmail() != null && !request.getEmail().equals(patient.getEmail())) {
			if (patientRepository.existsByEmail(request.getEmail())) {
				throw new DuplicateEmailException(request.getEmail());
			}
			patient.setEmail(request.getEmail());
		}
		if (request.getPhone() != null) {
			patient.setPhone(request.getPhone());
		}
		if (request.getDateOfBirth() != null) {
			patient.setDateOfBirth(request.getDateOfBirth());
		}

		return patientRepository.save(patient);
	}

	@Transactional
	public void delete(Long id) {
		if (!patientRepository.existsById(id)) {
			throw new PatientNotFoundException(id);
		}
		patientRepository.deleteById(id);
	}
}
