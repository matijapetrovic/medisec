package com.medisec.hospitalservice.web;

import com.medisec.hospitalservice.certificate.CertificateResponse;
import com.medisec.hospitalservice.certificate.CertificateService;
import com.medisec.hospitalservice.certificate.RevokeCertificateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {
    private final CertificateService certificateService;

    @GetMapping(value = "")
    public ResponseEntity<List<CertificateResponse>> findAll() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, CRLException, IOException, ClassNotFoundException {
        return ResponseEntity.ok(certificateService.readAllCertificates());
    }

    @PostMapping(value="/{sn}/revoke")
    public ResponseEntity<Void> revokeCertificate(@RequestBody RevokeCertificateRequest request, @PathVariable String sn) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        certificateService.revokeCertificate(request, sn);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
