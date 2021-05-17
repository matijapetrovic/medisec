package com.medisec.adminservice.domain.certificate_request;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRequestRepository extends JpaRepository<CertificateSigningRequest, Long> {
}
