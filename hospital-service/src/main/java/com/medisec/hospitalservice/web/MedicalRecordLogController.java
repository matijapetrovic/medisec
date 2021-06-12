package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.exception.PatientDoesNotExist;
import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordLog;
import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordLogRequest;
import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/medical-record-log", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordLogController {
    private final MedicalRecordLogService medicalRecordService;

    @PostMapping("")
    public ResponseEntity<Void> save(@RequestBody MedicalRecordLogRequest request) {
        System.out.println(request);
        medicalRecordService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordLog> findByPatientId(@PathVariable Long id) throws PatientDoesNotExist {
        return ResponseEntity.ok(medicalRecordService.findByPatientId(id));
    }

    @GetMapping("")
    public ResponseEntity<List<MedicalRecordLog>> findAll() {
        return ResponseEntity.ok(medicalRecordService.findAll());
    }
}
