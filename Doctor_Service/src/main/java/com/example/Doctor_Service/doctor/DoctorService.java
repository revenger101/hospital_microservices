package com.example.Doctor_Service.doctor;

import com.example.Doctor_Service.doctor.dto.CreateDoctorRequest;
import com.example.Doctor_Service.doctor.dto.UpdateDoctorRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {
	private final DoctorRepository doctorRepository;

	public DoctorService(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}

	@Transactional
	public Doctor create(CreateDoctorRequest request) {
		Doctor doctor = new Doctor(request.getName(), request.getSpecialty(), request.isActive());
		return doctorRepository.save(doctor);
	}

	@Transactional(readOnly = true)
	public Doctor getById(Long id) {
		return doctorRepository.findById(id).orElseThrow(() -> new DoctorNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public List<Doctor> list() {
		return doctorRepository.findAll();
	}

	@Transactional
	public Doctor update(Long id, UpdateDoctorRequest request) {
		Doctor doctor = getById(id);
		doctor.setName(request.getName());
		doctor.setSpecialty(request.getSpecialty());
		doctor.setActive(request.isActive());
		return doctorRepository.save(doctor);
	}

	@Transactional
	public void delete(Long id) {
		Doctor doctor = getById(id);
		doctorRepository.delete(doctor);
	}

	@Transactional(readOnly = true)
	public boolean isAvailable(Long doctorId) {
		return getById(doctorId).isActive();
	}
}
