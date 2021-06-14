package com.medisec.adminservice.domain.extension;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Embeddable;

@Getter
@Data
public class CertificateKeyUsage {
    @Field
    boolean crlSign;
    @Field
    boolean dataEncipherment;
    @Field
    boolean decipherOnly;
    @Field
    boolean keyAgreement;
    @Field
    boolean keyCertSign;
    @Field
    boolean keyEncipherment;
    @Field
    boolean nonRepudiation;
    @Field
    boolean digitalSignature;
    @Field
    boolean encipherOnly;

    @Field
    boolean keyUsageIsCritical;
}
