package com.medisec.adminservice.csr;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CsrService {
    private final CsrRepository csrRepository;

    public void createCsr(Csr csr) {
        csrRepository.save(csr);
    }

    public List<Csr> getCsrs() {
        return csrRepository.findAll();
    }

}
