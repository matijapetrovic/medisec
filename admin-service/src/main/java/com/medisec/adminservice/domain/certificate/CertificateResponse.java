package com.medisec.adminservice.certificate;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class CertificateResponse {
    String serialNumber;
    String name;
    String surname;
    String countryCode;
    String email;
    String organization;
    String organizationUnitName;

    String subjectId;

    Date startDate;
    Date endDate;

    boolean revoked;

    public static CertificateResponse of(
            BigInteger serialNumber,
            String name,
            String surname,
            String countryCode,
            String email,
            String organization,
            String organizationUnitName,
            String subjectId,
            Date startDate,
            Date endDate,
            boolean revoked) {
        return new CertificateResponse(
                serialNumber.toString(16),
                name,
                surname,
                countryCode,
                email,
                organization,
                organizationUnitName,
                subjectId,
                startDate,
                endDate,
                revoked);
    }
}
