package com.medisec.adminservice.controller;

import com.medisec.adminservice.csr.Csr;
import com.medisec.adminservice.csr.CsrService;
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
@RequestMapping(value="/api/csr", produces = MediaType.APPLICATION_JSON_VALUE)
public class CsrController {
    private final CsrService csrService;

    @PostMapping
    public ResponseEntity<Void> createCsr(@RequestBody Csr csr) {
        csrService.createCsr(csr);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
