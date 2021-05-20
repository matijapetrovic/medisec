package com.medisec.adminservice.web.template;

import com.medisec.adminservice.domain.extension.CertificateExtensions;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTemplateRequest {
    private String name;
    private CertificateExtensions extensions;
}
