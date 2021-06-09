package com.medisec.hospitalservice;

import com.medisec.hospitalservice.firewall.FirewallAlarm;
import com.medisec.hospitalservice.medical_record.MedicalRecordAlarm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class HospitalServiceApplication {

	public static void main(String[] args) {SpringApplication.run(HospitalServiceApplication.class, args);}

	@Bean
	public CommandLineRunner run(
			MedicalRecordAlarm medicalRecordAlarm,
			FirewallAlarm firewallAlarm
	) {
		return args -> {
			medicalRecordAlarm.run();
			firewallAlarm.run();
		};
	}
}

