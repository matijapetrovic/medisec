package com.medisec.adminservice.domain.extension;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Data
@NoArgsConstructor
public class CertificateExtensions {
    @Embedded
    CertificateBasicConstraints basicConstraints;
    @Embedded
    CertificateKeyUsage keyUsage;
}
