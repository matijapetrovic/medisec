package com.medisec.adminservice.web.certificate;

import com.medisec.adminservice.domain.certificate.CertificateResponse;
import com.medisec.adminservice.domain.certificate.RevocationReason;
import com.medisec.adminservice.exception.AliasNotValidException;
import com.medisec.adminservice.exception.CSRNotVerifiedException;
import com.medisec.adminservice.exception.MissingPrivateKeyException;
import com.medisec.adminservice.domain.certificate.CertificateService;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.*;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping("")
    public ResponseEntity<Void> issueCertificate(@Valid @RequestBody IssueCertificateRequest request) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, OperatorCreationException, KeyStoreException, MissingPrivateKeyException, IOException, InvalidKeyException, CSRNotVerifiedException, NoSuchProviderException {
        certificateService.issueCertificate(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{sn}/revoke")
    public ResponseEntity<Void> revokeCertificated(@RequestBody RevokeCertificateRequest request, @PathVariable String sn) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyStoreException, OperatorCreationException {
        certificateService.revokeCertificate(sn, request.getReason(), request.getAlias());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("")
    public ResponseEntity<List<CertificateResponse>> readAllCertificates() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, AliasNotValidException, IOException, CRLException {
        return ResponseEntity.ok(certificateService.readAllCertificates());
    }

    @GetMapping("/issuer-aliases")
    public ResponseEntity<List<String>> getIssuerAliases() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        return ResponseEntity.ok(certificateService.getIssuerAliases());
    }

    @GetMapping("revocation-reasons")
    public ResponseEntity<List<RevocationReasonDTO>> getRevocationReasons() {
        List<RevocationReasonDTO> reasons =
                Arrays.stream(RevocationReason.values())
                        .map(revocationReason ->
                                new RevocationReasonDTO(
                                        revocationReason.getKey(),
                                        revocationReason.getDesc()))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(reasons);
    }


}
