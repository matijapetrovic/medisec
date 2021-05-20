package com.medisec.adminservice.web.certificate;

import com.medisec.adminservice.domain.extension.CertificateExtensions;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
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

    private CertificateExtensions extensions;

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
