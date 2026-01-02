package com.example.Appointment_Service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "doctor-service")
public interface DoctorClient {
	@GetMapping("/demo/doctor/check")
	Map<String, Object> check(@RequestParam("doctorId") Long doctorId, @RequestParam("patientId") Long patientId);
}
