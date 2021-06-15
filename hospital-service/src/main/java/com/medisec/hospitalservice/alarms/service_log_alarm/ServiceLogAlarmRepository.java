package com.medisec.hospitalservice.alarms.service_log_alarm;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ServiceLogAlarmRepository extends MongoRepository<ServiceLogAlarm, String> {
    List<ServiceLogAlarm> findAllByTimeAfter(Date date);
}
