package com.medisec.adminservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.PublicKey;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IssueCertificateRequest {

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

    public String getFullName() {
        return name + ' ' + surname;
    }
}
