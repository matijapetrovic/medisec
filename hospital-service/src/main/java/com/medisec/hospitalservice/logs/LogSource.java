package com.medisec.hospitalservice.logs;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class LogSource {
    @NotBlank
    private String path;
    @Positive
    private int readFrequency;
    @NotNull
    private String filter;
}
