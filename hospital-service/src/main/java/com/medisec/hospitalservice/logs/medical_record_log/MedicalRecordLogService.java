package com.medisec.hospitalservice.logs.medical_record_log;

import com.medisec.hospitalservice.exception.PatientDoesNotExist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalRecordLogService {
    private final MedicalRecordLogRepository medicalRecordRepository;

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
