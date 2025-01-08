package com.microservice.pagos.microservice_pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MicroservicePagosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicePagosApplication.class, args);
	}

}
