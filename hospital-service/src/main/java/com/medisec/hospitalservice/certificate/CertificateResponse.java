package com.medisec.hospitalservice.certificate;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
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
}
