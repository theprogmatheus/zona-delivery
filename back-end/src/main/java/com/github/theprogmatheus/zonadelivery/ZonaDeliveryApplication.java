package com.github.theprogmatheus.zonadelivery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZonaDeliveryApplication {

	public static final Logger log = LoggerFactory.getLogger(ZonaDeliveryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ZonaDeliveryApplication.class, args);
	}

}