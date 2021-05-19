package com.medisec.hospitalservice.csr;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CertificateSigningRequest {
    @NotBlank(message="Common name must not be blank")
    private String commonName;
    @NotBlank(message="Given name must not be blank")
    private String givenName;
    @NotBlank(message="Username must not be blank")
    private String surname;
    @NotBlank(message="Surname must not be blank")
    private String organization;
    @NotBlank(message="Organization must not be blank")
    private String organizationUnit;
    @NotBlank(message="Coutry must not be blank")
    private String country;
    @Email
    private String email;
    @NotBlank(message="Unique identifier must not be blank")
    private String uniqueIdentifier;
}
