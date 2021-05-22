package com.medisec.adminservice.domain.certificate_request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="csr")
public class CertificateSigningRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="common_name")
    private String commonName;

    @Column(name="surname")
    private String surname;

    @Column(name="given_name")
    private String givenName;

    @Column(name="organization")
    private String organization;

    @Column(name="organization_unit")
    private String organizationUnit;

    @Column(name="country_code")
    private String countryCode;

    @Column(name="email")
    private String email;

    @Column(name="verified")
    private boolean verified;

    @Lob
    @Column(name="raw_csr", columnDefinition = "BLOB")
    private byte[] rawCsr;

    public void verify() {
        this.verified = true;
    }
}