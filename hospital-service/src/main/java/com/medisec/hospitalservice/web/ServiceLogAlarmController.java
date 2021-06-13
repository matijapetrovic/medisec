package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogAlarm;
import com.medisec.hospitalservice.alarms.service_log_alarm.ServiceLogAlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/service-log-alarm", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceLogAlarmController {
    private final ServiceLogAlarmService serviceLogAlarmService;

    @GetMapping("")
    public ResponseEntity<List<ServiceLogAlarm>> findAll() {
        return ResponseEntity.ok(serviceLogAlarmService.findAll());
    }
}
