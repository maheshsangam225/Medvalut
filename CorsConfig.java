package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration

public class CorsConfig {
	@Bean 
	
	
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings( CorsRegistry registry) {
			registry.addMapping("/**")
			.allowedOrigins("http://127.0.0.1:5500")
			.allowedMethods("GET","PUT","POST","DELETE", "PATCH")
			.allowedHeaders("*")
			.allowCredentials(true);
			}
		};
	}

}
