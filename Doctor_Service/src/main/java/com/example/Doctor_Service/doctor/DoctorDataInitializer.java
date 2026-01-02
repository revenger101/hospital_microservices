package com.example.Doctor_Service.doctor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DoctorDataInitializer {
	@Bean
	CommandLineRunner seedDoctors(DoctorRepository doctorRepository) {
		return args -> {
			if (doctorRepository.count() > 0) {
				return;
			}

			doctorRepository.save(new Doctor("Dr. Smith", "Cardiology", true));
			doctorRepository.save(new Doctor("Dr. Khan", "Dermatology", true));
		};
	}
}
