package com.medisec.hospitalservice.csr;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CertificateSigningRequest {
    @NotBlank
    private String commonName;
    @NotBlank
    private String givenName;
    @NotBlank
    private String surname;
    @NotBlank
    private String organization;
    @NotBlank
    private String organizationUnit;
    @NotBlank
    private String country;
    @NotBlank
    private String email;
    @NotBlank
    private String uniqueIdentifier;
}
