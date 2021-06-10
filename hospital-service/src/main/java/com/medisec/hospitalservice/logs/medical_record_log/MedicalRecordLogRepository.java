package com.medisec.hospitalservice.logs.medical_record_log;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface MedicalRecordLogRepository extends MongoRepository<MedicalRecordLog, String> {
    Optional<MedicalRecordLog> findByPatientId(Long id);
}
