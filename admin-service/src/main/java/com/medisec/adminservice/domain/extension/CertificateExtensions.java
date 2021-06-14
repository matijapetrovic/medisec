package com.medisec.adminservice.domain.extension;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;


@Data
@NoArgsConstructor
public class CertificateExtensions {
    CertificateBasicConstraints basicConstraints;
    CertificateKeyUsage keyUsage;
    @Field
    boolean subjectKeyId;
    @Field
    boolean authorityKeyId;
}
