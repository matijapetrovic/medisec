package com.medisec.hospitalservice;

import com.medisec.hospitalservice.medical_record.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class TestDroolsInterface {
    private final MedicalRecordService medicalRecordService;

    public void run() {
        System.out.println(medicalRecordService.findAll());
    }
}
