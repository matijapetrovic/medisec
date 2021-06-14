package com.medisec.adminservice.domain.template;

import com.medisec.adminservice.domain.extension.CertificateExtensions;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;


@Document(collection="template")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {
    @Field
    private String name;
    private CertificateExtensions extensions;
}
