package com.medisec.adminservice.domain.extension;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class CertificateKeyUsage {
    boolean crlSign;
    boolean dataEncipherment;
    boolean decipherOnly;
    boolean keyAgreement;
    boolean keyCertSign;
    boolean keyEncipherment;
    boolean nonRepudiation;
    boolean digitalSignature;
    boolean encipherOnly;

    boolean keyUsageIsCritical;
}
