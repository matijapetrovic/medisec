package com.medisec.hospitalservice.medical_record;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;

    public void save(MedicalRecordRequest request) {
        MedicalRecord medicalRecord =
                new MedicalRecord(
                        request.getAge(),
                        request.getPatientId(),
                        request.getTime(),
                        request.getHeartBeat(),
                        request.getAverageHeardBeat(),
                        request.getBloodPressure(),
                        request.getAverageBloodPressure(),
                        request.getBodyTemperature(),
                        request.isVaccinated(),
                        request.getSurgery()
                        );

        medicalRecordRepository.save(medicalRecord);
    }
}
