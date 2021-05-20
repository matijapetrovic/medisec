package com.medisec.adminservice.domain.extension;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class CertificateExtensions {
    CertificateExtensions.CertificateBasicConstraints basicConstraints;
    CertificateExtensions.CertificateKeyUsage keyUsage;

    @Getter
    public static class CertificateKeyUsage {
        boolean crlSign;
        boolean dataEncipherment;
        boolean decipherOnly;
        boolean keyAgreement;
        boolean keyCertSign;
        boolean keyEncipherment;
        boolean nonRepudiation;
        boolean digitalSignature;
        boolean encipherOnly;

        boolean isCritical;
    }

    @Getter
    public static class CertificateBasicConstraints {
        boolean isCa;
        Integer pathLen;

        boolean isCritical;
    }
}
