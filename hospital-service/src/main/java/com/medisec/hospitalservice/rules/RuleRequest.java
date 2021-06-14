package com.medisec.hospitalservice.rules;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RuleRequest {
    @NotBlank
    String name;
    @NotBlank
    String content;
}
