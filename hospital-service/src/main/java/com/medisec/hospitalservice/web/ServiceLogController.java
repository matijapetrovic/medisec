package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.logs.medical_record_log.MedicalRecordLog;
import com.medisec.hospitalservice.logs.service_log.ServiceLog;
import com.medisec.hospitalservice.logs.service_log.ServiceLogRequest;
import com.medisec.hospitalservice.logs.service_log.ServiceLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/service-log", produces = MediaType.APPLICATION_JSON_VALUE)
public class ServiceLogController {
    private final ServiceLogService serviceLogService;

    @PostMapping("")
    public ResponseEntity<Void> save(@RequestBody ServiceLogRequest request) {
        serviceLogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ServiceLog>> search(@RequestParam(required = false, defaultValue = "") String sourceIp,
                                                   @RequestParam(required = false, defaultValue = "") String destIp,
                                                   @RequestParam(required = false, defaultValue = "") String path,
                                                   @RequestParam(required = false, defaultValue = "") String protocol,
                                                   @RequestParam(required = false, defaultValue = "-1") int status,
                                                   @RequestParam(required = false)
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date time
                                                   ) {
        return ResponseEntity.ok(serviceLogService.search(sourceIp, destIp, path, protocol, status, time));
    }
}
