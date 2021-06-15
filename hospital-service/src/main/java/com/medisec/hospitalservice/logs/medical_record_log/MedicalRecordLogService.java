package com.medisec.hospitalservice.logs.medical_record_log;

import com.medisec.hospitalservice.alarms.medical_record_alarm.MedicalRecordAlarmService;
import com.medisec.hospitalservice.exception.PatientDoesNotExist;
import com.medisec.hospitalservice.web.config.KnowledgeSessionHelper;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalRecordLogService {
    private final MedicalRecordLogRepository medicalRecordRepository;
    private final MedicalRecordAlarmService medicalRecordAlarmService;
    private KieSession kSession = KnowledgeSessionHelper.generateKieSession();

    public void save(MedicalRecordLogRequest request) {
        MedicalRecordLog medicalRecord =
                new MedicalRecordLog(
                        UUID.randomUUID().toString(),
                        request.getAge(),
                        request.getPatientId(),
                        request.getTime(),
                        request.getHeartBeat(),
                        request.getAverageHeartBeat(),
                        request.getBloodPressure(),
                        request.getAverageBloodPressure(),
                        request.getBodyTemperature(),
                        request.isVaccinated(),
                        request.getSurgery()
                        );

        kSession.setGlobal("deviceAlarmService", medicalRecordAlarmService);
        kSession.insert(medicalRecord);
        kSession.fireAllRules();
        kSession.delete(kSession.getFactHandle(medicalRecord));

        medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecordLog findByPatientId(Long id) throws PatientDoesNotExist {
        return medicalRecordRepository
                .findByPatientId(id).orElseThrow(() -> new PatientDoesNotExist(String.format("Patiend with id: {%d} does not exist!", id)));
    }

    public List<MedicalRecordLog> findAll() {
        return medicalRecordRepository.findAll();
    }
}
