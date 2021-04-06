package com.medisec.adminservice.controller;

import com.medisec.adminservice.CertificateResponse;
import com.medisec.adminservice.exception.AliasNotValidException;
import com.medisec.adminservice.exception.MissingPrivateKeyException;
import com.medisec.adminservice.request.IssueCertificateRequest;
import com.medisec.adminservice.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping("")
    public ResponseEntity<Void> issueCertificate(IssueCertificateRequest request) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, OperatorCreationException, KeyStoreException, MissingPrivateKeyException, IOException, InvalidKeyException {
        certificateService.issueCertificate(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    public ResponseEntity<List<CertificateResponse>> readAllCertificates() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, AliasNotValidException, IOException {
        return ResponseEntity.ok(certificateService.readAllCertificates());
    }


}
