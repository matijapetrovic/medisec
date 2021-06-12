package com.medisec.adminservice.domain.extension;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Data
@Embeddable
public class CertificateBasicConstraints {
    boolean ca;
    Integer pathLen;

    boolean basicConstraintsIsCritical;
}
