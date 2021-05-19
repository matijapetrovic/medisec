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
        @NotBlank
        String subjectId;
        @NotBlank
        String givenName;
        @NotBlank
        String surname;
        @NotBlank
        String countryCode;
        @Email
        String email;
        @NotBlank
        String organization;
        @NotBlank

        String organizationUnitName;

        public String getFullName() {
            return givenName + ' ' + surname;
        }
    }



}
