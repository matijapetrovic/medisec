package com.medisec.adminservice.domain.certificate;

import com.medisec.adminservice.domain.crypto.pki.data.SubjectData;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import java.math.BigInteger;
import java.security.PublicKey;
import java.util.Date;
import java.util.Random;

public class SubjectDataBuilder {
    private PublicKey publicKey;

    private Date startDate;
    private Date endDate;

    private String subjectId;
    private String commonName;
    private String surname;
    private String givenName;
    private String organization;
    private String organizationUnit;
    private String countryCode;
    private String email;

    public SubjectData build() {
        Random rand = new Random();
        BigInteger serialNumber = new BigInteger(128, rand);

        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, commonName);
        builder.addRDN(BCStyle.SURNAME, surname);
        builder.addRDN(BCStyle.GIVENNAME, givenName);
        builder.addRDN(BCStyle.O, organization);
        builder.addRDN(BCStyle.OU, organizationUnit);
        builder.addRDN(BCStyle.C, countryCode);
        builder.addRDN(BCStyle.E, email);
        builder.addRDN(BCStyle.UID, subjectId);

        return new SubjectData(
                publicKey,
                builder.build(),
                serialNumber.toString(16),
                startDate,
                endDate
        );
    }

    public SubjectDataBuilder(PublicKey publicKey, Date startDate, Date endDate) {
        this.publicKey = publicKey;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SubjectDataBuilder withSubjectId(String subjectId) {
        this.subjectId = subjectId;
        return this;
    }
    public SubjectDataBuilder withCommonName(String commonName) {
        this.commonName = commonName;
        return this;
    }

    public SubjectDataBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }
    public SubjectDataBuilder withGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }
    public SubjectDataBuilder withOrganization(String organization) {
        this.organization = organization;
        return this;
    }
    public SubjectDataBuilder withOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
        return this;
    }
    public SubjectDataBuilder withCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }
    public SubjectDataBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

}

