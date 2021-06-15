package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.reports.ChartAlarmData;
import com.medisec.hospitalservice.reports.ReportService;
import com.medisec.hospitalservice.reports.ServiceLogReport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/reports")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/service-log")
    public ResponseEntity<ServiceLogReport> getServiceLogReport() throws IOException {
        return ResponseEntity.ok(reportService.getServiceLogReport());
    }

    @GetMapping("/service-log-alarm")
    public ResponseEntity<ChartAlarmData> getServiceLogAlarms(){
        return ResponseEntity.ok(reportService.getServiceAlarmData());
    }

}
