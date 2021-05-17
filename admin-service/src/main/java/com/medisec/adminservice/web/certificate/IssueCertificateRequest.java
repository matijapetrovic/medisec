package com.medisec.adminservice.web.certificate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.security.PublicKey;
import java.util.Date;


@Data
@NoArgsConstructor
public class IssueCertificateRequest {
    @Positive
    private Long csrId;
    @Valid
    private SubjectData subjectData;

    @FutureOrPresent
    private Date startDate;
    @FutureOrPresent
    private Date endDate;

    @Getter
    public static class SubjectData {
        String subjectId;
        String givenName;
        String surname;
        String countryCode;
        @Email
        String email;
        String organization;
        String organizationUnitName;

        public String getFullName() {
            return givenName + ' ' + surname;
        }
    }



}
