package com.medisec.hospitalservice;

import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class TestDroolsInterface {
    private final MedicalRecordLogService medicalRecordService;

    public void run() {
        System.out.println(medicalRecordService.findAll());
    }
}
