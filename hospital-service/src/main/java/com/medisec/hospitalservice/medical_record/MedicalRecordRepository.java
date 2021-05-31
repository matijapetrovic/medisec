package com.medisec.hospitalservice.medical_record;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicalRecordRepository extends MongoRepository<MedicalRecord, Long> {

}
