package com.medisec.hospitalservice.medical_record;

import com.medisec.hospitalservice.exception.PatientDoesNotExist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

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
                        request.getAverageHeartBeat(),
                        request.getBloodPressure(),
                        request.getAverageBloodPressure(),
                        request.getBodyTemperature(),
                        request.isVaccinated(),
                        request.getSurgery()
                        );

        medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord findByPatientId(Long id) throws PatientDoesNotExist {
        return medicalRecordRepository
                .findByPatientId(id).orElseThrow(() -> new PatientDoesNotExist(String.format("Patiend with id: {%d} does not exist!", id)));
    }

    public List<MedicalRecord> findAll() {
        return medicalRecordRepository.findAll();
    }
}
