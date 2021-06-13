package com.medisec.hospitalservice;

import com.medisec.hospitalservice.alarms.service_log_alarm.LogHandler;
import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogAlarm;
import com.medisec.hospitalservice.alarms.medical_record_alarm.MedicalRecordAlarmGenerator;
import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogsAlarmGenerator;
import com.medisec.hospitalservice.logs.service_log.ServiceLogAlarm;
import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordAlarmGenerator;
import com.medisec.hospitalservice.patient.Patient;
import com.medisec.hospitalservice.patient.PatientDetails;
import com.medisec.hospitalservice.patient.PatientRepository;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class HospitalServiceApplication {

	public static void main(String[] args) {SpringApplication.run(HospitalServiceApplication.class, args);}

	@Bean
	public CommandLineRunner run(
			ServiceLogsAlarmGenerator serviceLogsAlarmGenerator
			MedicalRecordAlarmGenerator medicalRecordAlarmGenerator,
			ServiceLogAlarm firewallAlarm,
			PatientRepository patientRepository
	) {
		return args -> {
			patientRepository.insert(new Patient(123L, "Djura", "Djuric", "1233245",
					new PatientDetails(
						30,
							120,
							120,
							new PatientDetails.BloodGroup(PatientDetails.BloodGroup.BloodType.A, true),
							new PatientDetails.Dioptry(0.5, 0.5),
							List.of(new PatientDetails.Vaccination("COVID", new Date())),
							List.of(new PatientDetails.Surgery("Eye surgery", new Date()))
					)));
			serviceLogsAlarmGenerator.run();
			medicalRecordAlarmGenerator.run();
			firewallAlarm.run();
		};
	}
}

