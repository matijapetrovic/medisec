package com.medisec.hospitalservice.medical_record;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MedicalRecordRepository extends MongoRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findByPatientId(Long id);
}
