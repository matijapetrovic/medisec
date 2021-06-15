package com.medisec.adminservice;

import com.medisec.adminservice.domain.certificate.CertificateService;
import com.medisec.adminservice.domain.certificate_request.CertificateRequestRepository;
import com.medisec.adminservice.domain.certificate_request.CertificateSigningRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.FileWriter;
import java.security.Security;

@SpringBootApplication
@EnableAsync
public class AdminServiceApplication {

	public static void main(String[] args) { SpringApplication.run(AdminServiceApplication.class, args); }

	@Bean
	public CommandLineRunner run(CertificateRequestRepository certificateRequestRepository, CertificateService certificateService) {
		return args -> {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			for (String issuer: certificateService.getIssuerAliases()) {
				certificateService.initRevocationList("1", issuer);
			}

		};
	}
}
