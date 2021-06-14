package com.medisec.adminservice.domain.extension;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Embeddable;

@Getter
@Data
public class CertificateBasicConstraints {
    @Field
    boolean ca;
    @Field
    Integer pathLen;
    @Field
    boolean basicConstraintsIsCritical;
}
