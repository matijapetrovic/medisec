package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.logs.LogSource;
import com.medisec.hospitalservice.logs.LogSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LogSourceController {
    private final LogSourceService service;

    public ResponseEntity<Void> updateSources(@Valid @RequestBody List<LogSource> sources) throws IOException {
        service.updateSources(sources);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<List<LogSource>> getSources() {
        return ResponseEntity.ok(service.getSources());
    }
}
