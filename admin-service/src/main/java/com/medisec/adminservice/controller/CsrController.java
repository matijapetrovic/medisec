package com.medisec.adminservice.controller;

import com.medisec.adminservice.csr.CsrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/csr", produces = MediaType.APPLICATION_JSON_VALUE)
public class CsrController {
    private final CsrService csrService;

    @PostMapping("")
    public ResponseEntity<Void> createCsr(@RequestBody byte[] csr) throws IOException {
        csrService.createCsr(csr);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/verify/{id}")
    public ResponseEntity<Void> verifyCsr(@PathVariable Long id) {
        csrService.verify(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
