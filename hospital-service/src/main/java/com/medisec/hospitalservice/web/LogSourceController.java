package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.logs.LogSource;
import com.medisec.hospitalservice.logs.LogSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/log-sources")
public class LogSourceController {
    private final LogSourceService service;

    @PostMapping("")
    public ResponseEntity<Void> updateSources(@Valid @RequestBody List<LogSource> sources) throws IOException {
        service.updateSources(sources);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<List<LogSource>> getSources() {
        return ResponseEntity.ok(service.getSources());
    }
}
