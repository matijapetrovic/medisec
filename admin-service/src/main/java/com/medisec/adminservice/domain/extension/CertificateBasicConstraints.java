package com.medisec.adminservice.domain.extension;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class CertificateBasicConstraints {
    boolean isCa;
    Integer pathLen;

    boolean basicConstraintsIsCritical;
}
