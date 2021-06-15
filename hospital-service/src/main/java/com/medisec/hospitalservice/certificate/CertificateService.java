package com.medisec.hospitalservice.certificate;

import com.medisec.hospitalservice.certificate.keystore.KeyStoreReader;
import com.medisec.hospitalservice.client.AdminClient;
import com.medisec.hospitalservice.exception.AliasNotValidException;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificateService     {
    private final KeyStoreReader keyStoreReader;
    private final AdminClient adminClient;

    public List<CertificateResponse> readAllCertificates() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, AliasNotValidException, CRLException, ClassNotFoundException {
        List<CertificateResponse> certificates = new ArrayList<>();

        Enumeration<String> aliases = keyStoreReader.getAllAliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            Optional<Certificate> certificateOptional = keyStoreReader.readCertificate(alias);
            if (certificateOptional.isEmpty()) continue;
            Certificate certificate = certificateOptional.get();
            JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) certificate);
            X500Name subject = certHolder.getSubject();
            CertificateResponse certificateResponse = CertificateResponse.builder()
                    .countryCode(CertificateRequestExtractor.getField(subject, BCStyle.C))
                    .email(CertificateRequestExtractor.getField(subject, BCStyle.E))
                    .subjectId(CertificateRequestExtractor.getField(subject, BCStyle.UID))
                    .name(CertificateRequestExtractor.getField(subject, BCStyle.GIVENNAME))
                    .surname(CertificateRequestExtractor.getField(subject, BCStyle.SURNAME))
                    .organization(CertificateRequestExtractor.getField(subject, BCStyle.O))
                    .organizationUnitName(CertificateRequestExtractor.getField(subject, BCStyle.OU))
                    .startDate(certHolder.getNotBefore())
                    .endDate(certHolder.getNotAfter())
                    .serialNumber(certHolder.getSerialNumber().toString(16))
                    .revoked(isRevoked(certificate))
                    .build();
            certificates.add(certificateResponse);
        }

        return certificates;
    }

    public void revokeCertificate(RevokeCertificateRequest request, String serialNumber) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        String alias = this.getIssuerAlias(serialNumber);
        request.setReason(1);
        String revokeData = String.format("alias=%s",alias);
        StringBuilder builder = new StringBuilder(revokeData);

        adminClient.revokeCertificate(alias, serialNumber);
    }

    private boolean isRevoked(Certificate certificate) throws IOException, CertificateException, CRLException, ClassNotFoundException {
       byte[] request = serializeCertificate(certificate);
       return adminClient.isCertificateRevoked(request);
    }

    private byte[] serializeCertificate(Certificate cert) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(cert);
        oos.flush();
        return bos.toByteArray();
    }

    private String getIssuerAlias(String serialNumber) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        String issuerAlias = "";
        Enumeration<String> aliases = keyStoreReader.getAllAliases();
        while(aliases.hasMoreElements()){
            String alias = aliases.nextElement();
            Optional<Certificate> certificateOptional = keyStoreReader.readCertificate(alias);
            if (certificateOptional.isEmpty()) continue;
            Certificate certificate = certificateOptional.get();
            JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) certificate);
            if(certHolder.getSerialNumber().toString(16).equals(serialNumber)){
                X500Name issuer = certHolder.getIssuer();
                issuerAlias = CertificateRequestExtractor.getField(issuer, BCStyle.E);
            }
        }

        return issuerAlias;
    }

}
