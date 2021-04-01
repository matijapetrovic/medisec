package com.medisec.adminservice.service;

import com.medisec.adminservice.Certificate;
import com.medisec.adminservice.crypto.pki.data.IssuerData;
import com.medisec.adminservice.crypto.pki.data.SubjectData;

import java.util.List;

public interface CertificateService {
    // TODO: Ovo treba sve prepraviti
    void issueCertificate(IssuerData issuerData, SubjectData subjectData);
    List<Certificate> readAllCertificates();
    void revokeCertificate(String serialNumber);
    Certificate distributeCertificate();
}
