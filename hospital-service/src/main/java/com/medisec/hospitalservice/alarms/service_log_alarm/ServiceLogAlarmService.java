package com.medisec.hospitalservice.alarms.service_log_alarm;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceLogAlarmService {
    private final ServiceLogAlarmRepository serviceLogAlarmRepository;

    public void save(ServiceLogAlarm serviceLogAlarm) { serviceLogAlarmRepository.save(serviceLogAlarm); }

    public List<ServiceLogAlarm> findAll() {
        return serviceLogAlarmRepository.findAll();
    }
}
