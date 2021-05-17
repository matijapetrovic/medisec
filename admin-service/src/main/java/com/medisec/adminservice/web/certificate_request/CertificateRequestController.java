package com.medisec.adminservice.web.certificate_request;

import com.medisec.adminservice.domain.certificate_request.CertificateSigningRequest;
import com.medisec.adminservice.domain.certificate_request.CertificateRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/certificate-request", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateRequestController {
    private final CertificateRequestService certificateRequestService;

    @PostMapping("")
    public ResponseEntity<Void> createCertificateRequest(@RequestBody byte[] certificateRequest) throws IOException {
        certificateRequestService.createCertificateRequest(certificateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/verify/{id}")
    public ResponseEntity<Void> verifyCertificateRequest(@PathVariable Long id) {
        certificateRequestService.veriftCertificateRequest(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("")
    public ResponseEntity<List<CertificateSigningRequest>> getAllRequests() {
        return ResponseEntity.ok(certificateRequestService.getCsrs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateSigningRequest> findById(@PathVariable Long id) {
        return ResponseEntity.ok(certificateRequestService.findById(id));
    }
}
