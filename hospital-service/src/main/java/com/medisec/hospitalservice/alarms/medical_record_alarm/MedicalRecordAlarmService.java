package com.medisec.hospitalservice.alarms.medical_record_alarm;

import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordAlarm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalRecordAlarmService {
    private final MedicalRecordAlarmRepository medicalRecordAlarmRepository;

    public void save(MedicalRecordAlarm medicalRecordAlarm) {
        medicalRecordAlarmRepository.save(medicalRecordAlarm);
    }
}
