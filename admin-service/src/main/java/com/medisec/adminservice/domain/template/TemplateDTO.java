package com.medisec.adminservice.domain.template;

import lombok.Data;

@Data
public class TemplateDTO {
    private final Long id;
    private final String name;
    private final String extensions;
}
