package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.patient.Patient;
import com.medisec.hospitalservice.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("")
    public ResponseEntity<List<Patient>> getPatients() {
        return ResponseEntity.ok(patientService.getPatients());
    }
}
