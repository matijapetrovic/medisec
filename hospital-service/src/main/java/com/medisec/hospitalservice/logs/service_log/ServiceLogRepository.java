package com.medisec.hospitalservice.logs.service_log;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ServiceLogRepository extends MongoRepository<ServiceLog, String> {

}
