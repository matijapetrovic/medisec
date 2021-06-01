package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.firewall.FirewallLogRequest;
import com.medisec.hospitalservice.firewall.FirewallLogService;
import com.medisec.hospitalservice.medical_record.MedicalRecordRequest;
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
@RequestMapping(value="/api/firewall-log", produces = MediaType.APPLICATION_JSON_VALUE)
public class FirewallLogController {
    private final FirewallLogService firewallLogService;

    @PostMapping("")
    public ResponseEntity<Void> save(@RequestBody FirewallLogRequest request) {
        firewallLogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
