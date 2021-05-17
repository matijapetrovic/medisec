package com.medisec.adminservice.web.certificate;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class RevokeCertificateRequest {
    @Min(value=0, message="Revocation reason must be between 0 and 10, excluding 7")
    @Max(value=10, message="Revocation reason must be between 0 and 10, excluding 7")
    // TODO Kabi validiraj da ne moze sedmica!!
    private Integer reason;

    @NotBlank(message="Certificate alias must not be blank")
    private String alias;
}
