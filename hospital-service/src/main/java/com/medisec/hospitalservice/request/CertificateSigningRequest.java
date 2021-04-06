package com.medisec.hospitalservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
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
