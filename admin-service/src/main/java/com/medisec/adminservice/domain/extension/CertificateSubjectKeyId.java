package com.medisec.adminservice.domain.extension;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Embeddable;

@Getter
public class CertificateSubjectKeyId {
    @Field
    boolean subjectKeyIdIsCritical;
}
