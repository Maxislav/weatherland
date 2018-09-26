package com.lipatov.weatherland.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lipatov.weatherland")
public class WeatherLandApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherLandApplication.class, args);
	}
}
