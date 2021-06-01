package com.medisec.adminservice.domain.extension;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class CertificateSubjectKeyId {
    boolean subjectKeyIdIsCritical;
}
