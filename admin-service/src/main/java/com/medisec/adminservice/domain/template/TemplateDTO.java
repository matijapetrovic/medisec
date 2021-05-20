package com.medisec.adminservice.domain.template;

import com.medisec.adminservice.domain.extension.CertificateExtensions;
import lombok.Data;

@Data
public class TemplateDTO {
    private final Long id;
    private final String name;
    private final CertificateExtensions extensions;
}
