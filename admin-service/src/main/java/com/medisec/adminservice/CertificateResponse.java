package com.medisec.adminservice;


import lombok.AllArgsConstructor;
import lombok.Setter;

import java.security.PublicKey;
import java.util.Date;

@Setter
@AllArgsConstructor
public class CertificateResponse {
    String name;
    String surname;
    String countryCode;
    String email;
    String organization;
    String organizationUnitName;

    String subjectId;

    PublicKey publicKey;

    Date startDate;
    Date endDate;

    boolean revoked;

    public static CertificateResponse of(
            String name,
            String surname,
            String countryCode,
            String email,
            String organization,
            String organizationUnitName,
            String subjectId,
            PublicKey publicKey,
            Date startDate,
            Date endDate,
            boolean revoked) {
        return new CertificateResponse(
                name,
                surname,
                countryCode,
                email,
                organization,
                organizationUnitName,
                subjectId,
                publicKey,
                startDate,
                endDate,
                revoked);
    }
}
