package com.medisec.hospitalservice.controller;


import com.medisec.hospitalservice.request.CertificateSigningRequest;
import com.medisec.hospitalservice.service.CSRService;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/hospital-service", produces = MediaType.APPLICATION_JSON_VALUE)
public class CSRController {
    private final CSRService csrService;

    @PostMapping("")
    public ResponseEntity<Void> sendCSR(@RequestBody CertificateSigningRequest csr) throws NoSuchAlgorithmException, IOException, NoSuchProviderException, OperatorCreationException {
        csrService.sendCSR(csr);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
