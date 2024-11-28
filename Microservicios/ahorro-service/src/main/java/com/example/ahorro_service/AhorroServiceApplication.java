package com.example.ahorro_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class AhorroServiceApplication {

	public static void main(String[] args) {SpringApplication.run(AhorroServiceApplication.class, args);}
	@Bean
	public TomcatServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.setPort(8082); // Change the port here
		return factory;
	}
}