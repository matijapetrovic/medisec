package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.medical_record.MedicalRecordRequest;
import com.medisec.hospitalservice.medical_record.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/medical-record", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @PostMapping("")
    public ResponseEntity<Void> save(@RequestBody MedicalRecordRequest request) {
        medicalRecordService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
