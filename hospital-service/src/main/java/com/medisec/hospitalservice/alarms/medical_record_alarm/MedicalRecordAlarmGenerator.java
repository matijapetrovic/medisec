package com.medisec.hospitalservice.alarms.medical_record_alarm;


import com.medisec.hospitalservice.alarms.medical_record_alarm.MedicalRecordAlarmService;
import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordLogService;
import lombok.RequiredArgsConstructor;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MedicalRecordAlarmGenerator {
    //private final MedicalRecordLogService service;
    //private final MedicalRecordAlarmService medicalRecordAlarmService;

    public void run() {
//        List<MedicalRecordLog> medicalRecords = service.findAll();

//        KieServices ks = KieServices.Factory.get();
//        KieContainer kContainer = ks.getKieClasspathContainer();
//        KieSession kSession = kContainer.newKieSession("ksession-rules");
//
//
//        kSession.setGlobal("service", medicalRecordAlarmService);
//        for(MedicalRecordLog record: medicalRecords) {
//            kSession.insert(record);
//            kSession.fireAllRules();
//            kSession.delete(kSession.getFactHandle(record));
//        }
    }
}
