package com.medisec.hospitalservice.alarms.service_log_alarm;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceLogAlarmRepository extends MongoRepository<ServiceLogAlarm, String> {
}
