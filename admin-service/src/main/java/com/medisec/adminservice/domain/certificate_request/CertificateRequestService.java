package com.medisec.adminservice.domain.certificate_request;

import com.medisec.adminservice.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateRequestService {
    private final CertificateRequestRepository certificateRequestRepository;
    private final EmailService emailService;

    public void createCertificateRequest(byte[] csr) throws IOException {
        PKCS10CertificationRequest request = CertificateRequestExtractor.extractCsr(csr);
        X500Name name = request.getSubject();
        CertificateSigningRequest saved = certificateRequestRepository.save(
                new CertificateSigningRequest(null,
                        CertificateRequestExtractor.getField(name, BCStyle.CN),
                        CertificateRequestExtractor.getField(name, BCStyle.SURNAME),
                        CertificateRequestExtractor.getField(name, BCStyle.GIVENNAME),
                        CertificateRequestExtractor.getField(name, BCStyle.O),
                        CertificateRequestExtractor.getField(name, BCStyle.OU),
                        CertificateRequestExtractor.getField(name, BCStyle.C),
                        CertificateRequestExtractor.getField(name, BCStyle.E),
                        false,
                        csr));
        emailService.sendEmail(saved.getEmail(), "CSR Verification", buildText(saved.getId()));
    }

    private String buildText(Long csrId){
        return "Please click the following link to verify your email in order to have your certificate signed: " +
                "http://localhost:8080/api/csr/verify/" + csrId + ".";
    }

    public List<CertificateSigningRequest> getCsrs() {
        return certificateRequestRepository.findAll();
    }

    public void veriftCertificateRequest(Long id) {
        CertificateSigningRequest certificateSigningRequest = certificateRequestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CSR Id invalid"));
        certificateSigningRequest.verify();
        certificateRequestRepository.save(certificateSigningRequest);
    }

    public CertificateSigningRequest findById(Long id) {
        return certificateRequestRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
