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

    Long csrId;

    String givenName;
    String surname;
    String countryCode;
    String email;
    String organization;
    String organizationUnitName;

    String subjectId;

    Date startDate;
    Date endDate;

    public String getFullName() {
        return givenName + ' ' + surname;
    }
}
