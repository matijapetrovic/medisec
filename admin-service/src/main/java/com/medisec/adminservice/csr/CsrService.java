package com.medisec.adminservice.csr;

import com.medisec.adminservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsrService {
    private final CsrRepository csrRepository;
    private final EmailService emailService;

    public void createCsr(Csr csr) {
        Csr saved = csrRepository.save(csr);
        emailService.sendEmail(csr.getEmail(), "CSR Verification", buildText(saved.getId()));
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
