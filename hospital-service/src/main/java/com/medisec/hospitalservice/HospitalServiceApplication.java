package com.medisec.hospitalservice;

import com.medisec.hospitalservice.alarms.service_log_alarm.LogHandler;
import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogAlarm;
import com.medisec.hospitalservice.alarms.medical_record_alarm.MedicalRecordAlarmGenerator;
import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogsAlarmGenerator;
import com.medisec.hospitalservice.certificate.keystore.KeyStoreReader;
import com.medisec.hospitalservice.crypto.keystores.KeyStoreWriter;
import com.medisec.hospitalservice.logs.LogReader;
import com.medisec.hospitalservice.logs.LogSource;
import com.medisec.hospitalservice.logs.LogSourceService;
import com.medisec.hospitalservice.logs.service_log.ServiceLogRepository;
import com.medisec.hospitalservice.patient.Patient;
import com.medisec.hospitalservice.patient.PatientDetails;
import com.medisec.hospitalservice.patient.PatientRepository;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class HospitalServiceApplication {

	@Value("${key.alias}")
	private String alias;

	public static void main(String[] args) {SpringApplication.run(HospitalServiceApplication.class, args);}
	public static X509Certificate extractCert(byte[] csr) throws IOException, CertificateException {
		ByteArrayInputStream bis = new ByteArrayInputStream(csr);
		Reader pemReader = new BufferedReader(new InputStreamReader(bis));
		PEMParser pemParser = new PEMParser(pemReader);

		Object parsedObj = pemParser.readObject();

		assert parsedObj instanceof X509CertificateHolder;
		return new JcaX509CertificateConverter().setProvider( "BC" )
				.getCertificate( ((X509CertificateHolder) parsedObj) );
	}
	@Bean
	public CommandLineRunner run(
			MedicalRecordAlarmGenerator medicalRecordAlarmGenerator,
			PatientRepository patientRepository,
			LogSourceService logSourceService,
			ServiceLogsAlarmGenerator serviceLogsAlarmGenerator,
			ServiceLogRepository serviceLogRepository,
			KeyStoreWriter keyStoreWriter,
			KeyStoreReader keyStoreReader
	) {
		return args -> {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			if (keyStoreReader.readCertificate(alias).isEmpty()) {
				byte[] privateKeyBytes = FileUtils.readFileToByteArray(new File("../hospital-service/src/main/resources/priv.key"));
				KeyFactory kf = KeyFactory.getInstance("RSA");
				PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
				File file = new File("../hospital-service/src/main/resources/certificate.cer");
				byte[] certContent = Files.readAllBytes(file.toPath());
				X509Certificate certificate = extractCert(certContent);

				keyStoreWriter.write(alias, privateKey, certificate);
			}
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
			createReaders(logSourceService, serviceLogRepository, serviceLogsAlarmGenerator);

		};
	}
	public void createReaders(LogSourceService logSourceService, ServiceLogRepository serviceLogRepository, ServiceLogsAlarmGenerator alarmGenerator) {
		List<LogSource> logSources = logSourceService.getSources();
		for (LogSource logSource: logSources) {
			LogReader logReader = new LogReader(logSource, serviceLogRepository, alarmGenerator);
			Thread t = new Thread(logReader);
			t.start();
		}
	}

}

