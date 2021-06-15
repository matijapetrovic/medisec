package com.medisec.adminservice.domain.certificate_request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CertificateRequestRepository extends MongoRepository<CertificateSigningRequest, String> {
}
