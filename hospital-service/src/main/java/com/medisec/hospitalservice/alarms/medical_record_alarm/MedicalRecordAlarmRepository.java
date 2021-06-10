package com.medisec.hospitalservice.alarms.medical_record_alarm;

import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordAlarm;
import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MedicalRecordAlarmRepository extends MongoRepository<MedicalRecordAlarm, String> {
}
