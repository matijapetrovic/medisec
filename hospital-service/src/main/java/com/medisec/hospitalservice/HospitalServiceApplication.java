package com.medisec.hospitalservice;

import com.medisec.hospitalservice.logs.service_log.ServiceLogAlarm;
import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordAlarmGenerator;
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
			MedicalRecordAlarmGenerator medicalRecordAlarmGenerator,
			ServiceLogAlarm firewallAlarm
	) {
		return args -> {
			medicalRecordAlarmGenerator.run();
			firewallAlarm.run();
		};
	}
}

