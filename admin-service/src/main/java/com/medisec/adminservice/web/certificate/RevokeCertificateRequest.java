package com.medisec.adminservice.web.certificate;

import com.medisec.adminservice.common.validation.annotations.RevocationReason;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class RevokeCertificateRequest {
    @RevocationReason
    private Integer reason;

    @NotBlank(message="Certificate alias must not be blank")
    private String alias;
}
