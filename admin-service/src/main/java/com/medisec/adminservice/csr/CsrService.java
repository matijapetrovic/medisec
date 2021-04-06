package com.medisec.adminservice.csr;

import com.medisec.adminservice.service.EmailService;
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
public class CsrService {
    private final CsrRepository csrRepository;
    private final EmailService emailService;

    public void createCsr(byte[] csr) throws IOException {
        PKCS10CertificationRequest request = CsrExtractor.extractCsr(csr);
        X500Name name = request.getSubject();
        Csr saved = csrRepository.save(
                new Csr(null,
                        CsrExtractor.getField(name, BCStyle.CN),
                        CsrExtractor.getField(name, BCStyle.SURNAME),
                        CsrExtractor.getField(name, BCStyle.GIVENNAME),
                        CsrExtractor.getField(name, BCStyle.O),
                        CsrExtractor.getField(name, BCStyle.OU),
                        CsrExtractor.getField(name, BCStyle.C),
                        CsrExtractor.getField(name, BCStyle.E),
                        false,
                        csr));
        emailService.sendEmail(saved.getEmail(), "CSR Verification", buildText(saved.getId()));
    }

    private String buildText(Long csrId){
        return "Please click the following link to verify your email in order to have your certificate signed: " +
                "http://localhost:8080/api/csr/verify/" + csrId + ".";
    }

    public List<Csr> getCsrs() {
        return csrRepository.findAll();
    }

    public void verify(Long id) {
        Csr csr = csrRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CSR Id invalid"));
        csr.verify();
        csrRepository.save(csr);
    }

}
