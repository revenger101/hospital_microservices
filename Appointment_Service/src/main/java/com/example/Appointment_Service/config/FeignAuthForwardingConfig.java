package com.example.Appointment_Service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignAuthForwardingConfig {
	@Bean
	RequestInterceptor authForwardingInterceptor() {
		return template -> {
			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			if (attrs == null) {
				return;
			}

			String authorization = attrs.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
			if (authorization == null || authorization.isBlank()) {
				return;
			}

			template.header(HttpHeaders.AUTHORIZATION, authorization);
		};
	}
}
