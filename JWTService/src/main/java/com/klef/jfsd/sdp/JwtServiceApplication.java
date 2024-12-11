package com.klef.jfsd.sdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class JwtServiceApplication {

	public static void main(String[] args) {
		Dotenv.load().entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		
		SpringApplication.run(JwtServiceApplication.class, args);
		System.out.println("Jwt Service Running...!");
	}

}
