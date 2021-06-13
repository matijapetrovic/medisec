package com.medisec.hospitalservice;

import com.medisec.hospitalservice.alarms.service_log_alarm.LogHandler;
import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogAlarm;
import com.medisec.hospitalservice.alarms.medical_record_alarm.MedicalRecordAlarmGenerator;
import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogsAlarmGenerator;
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
//			MedicalRecordAlarmGenerator medicalRecordAlarmGenerator,
			ServiceLogsAlarmGenerator serviceLogsAlarmGenerator
	) {
		return args -> {
//			medicalRecordAlarmGenerator.run();
			serviceLogsAlarmGenerator.run();
		};
	}
}

