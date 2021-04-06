package com.medisec.hospitalservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CertificateSigningRequest {
    private String commonName;
    private String givenName;
    private String surname;
    private String organization;
    private String organizationUnit;
    private String country;
    private String email;
    private String uniqueIdentifier;
}
