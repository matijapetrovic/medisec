package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.alarms.medical_record_alarm.MedicalRecordAlarmService;
import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordAlarm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/medical-record-alarm", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordAlarmControler {
    private final MedicalRecordAlarmService medicalRecordAlarmService;

    @GetMapping("")
    public ResponseEntity<List<MedicalRecordAlarm>> findAll(){
        return ResponseEntity.ok(medicalRecordAlarmService.findAll());
    }
}
