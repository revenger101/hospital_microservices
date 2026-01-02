package com.example.Patient_Service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "appointment-service")
public interface AppointmentClient {
	@GetMapping("/demo/appointment/create")
	Map<String, Object> create(@RequestParam("patientId") Long patientId, @RequestParam("doctorId") Long doctorId);
}
