package com.medisec.hospitalservice.alarms.medical_record_alarm;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicalRecordAlarmRepository extends MongoRepository<MedicalRecordAlarm, String> {
}
