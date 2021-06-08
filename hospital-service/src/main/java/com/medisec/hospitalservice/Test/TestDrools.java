package com.medisec.hospitalservice.Test;


import com.medisec.hospitalservice.medical_record.MedicalRecord;
import com.medisec.hospitalservice.medical_record.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDrools {
    private final MedicalRecordService service;

    public void run() {
        List<MedicalRecord> medicalRecords = service.findAll();

        // load up the knowledge base
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules");

        for(MedicalRecord record: medicalRecords) {
            kSession.insert(record);
            kSession.fireAllRules();
            kSession.delete(kSession.getFactHandle(record));
        }
    }
}