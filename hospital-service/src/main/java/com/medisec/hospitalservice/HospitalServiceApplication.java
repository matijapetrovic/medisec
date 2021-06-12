package com.medisec.hospitalservice;

import com.medisec.hospitalservice.logs.service_log.ServiceLogAlarm;
import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordAlarmGenerator;
import com.medisec.hospitalservice.patient.Patient;
import com.medisec.hospitalservice.patient.PatientDetails;
import com.medisec.hospitalservice.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class HospitalServiceApplication {

	public static void main(String[] args) {SpringApplication.run(HospitalServiceApplication.class, args);}

	@Bean
	public CommandLineRunner run(
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
			medicalRecordAlarmGenerator.run();
			firewallAlarm.run();
		};
	}
}

