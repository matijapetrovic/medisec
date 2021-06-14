package com.medisec.hospitalservice.certificate;

import com.medisec.hospitalservice.common.validation.annotations.RevocationReason;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class RevokeCertificateRequest {
    @RevocationReason
    private Integer reason;

    @NotBlank(message="Certificate alias must not be blank")
    private String alias;
}
