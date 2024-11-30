package com.example.credito_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CreditoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditoServiceApplication.class, args);
	}
}
