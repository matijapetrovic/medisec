package com.medisec.adminservice.domain.certificate_request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;

@Document(collection = "csr")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CertificateSigningRequest {
    @Id
    private String id;

    @Field(name="common_name")
    private String commonName;

    @Field(name="surname")
    private String surname;

    @Field(name="given_name")
    private String givenName;

    @Field(name="organization")
    private String organization;

    @Field(name="organization_unit")
    private String organizationUnit;

    @Field(name="country_code")
    private String countryCode;

    @Field(name="email")
    private String email;

    @Field(name="verified")
    private boolean verified;

    @Field(name="raw_csr")
    private byte[] rawCsr;

    public void verify() {
        this.verified = true;
    }
}
