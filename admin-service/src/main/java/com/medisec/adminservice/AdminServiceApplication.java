package com.medisec.adminservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.security.Security;

@SpringBootApplication
@EnableAsync
public class AdminServiceApplication {

	public static void main(String[] args) { SpringApplication.run(AdminServiceApplication.class, args); }

	@Bean
	public CommandLineRunner run() {
		return args -> {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		};
	}
}
