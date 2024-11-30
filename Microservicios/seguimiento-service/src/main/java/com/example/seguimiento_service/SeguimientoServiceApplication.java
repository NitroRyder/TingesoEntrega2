package com.example.seguimiento_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class SeguimientoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeguimientoServiceApplication.class, args);
	}
	@Bean
	public TomcatServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.setPort(8087); // Change the port here
		return factory;
	}
}
