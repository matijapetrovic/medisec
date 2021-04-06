package com.medisec.adminservice.service;

import com.medisec.adminservice.Certificate;
import com.medisec.adminservice.crypto.pki.certificates.CertificateGenerator;
import com.medisec.adminservice.crypto.pki.data.IssuerData;
import com.medisec.adminservice.crypto.pki.data.SubjectData;
import com.medisec.adminservice.crypto.pki.keystores.KeyStoreReader;
import com.medisec.adminservice.crypto.pki.keystores.KeyStoreWriter;
import com.medisec.adminservice.exception.MissingPrivateKeyException;
import com.medisec.adminservice.request.IssueCertificateRequest;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final KeyStoreWriter keyStoreWriter;
    private final KeyStoreReader keyStoreReader;

    public void issueCertificate(IssueCertificateRequest request) throws NoSuchAlgorithmException, CertificateException, OperatorCreationException, KeyStoreException, IOException, UnrecoverableKeyException, MissingPrivateKeyException {
        SubjectData subjectData = generateSubjectData(request);
        IssuerData issuerData = keyStoreReader.readIssuerFromStore("videcemo");
        PrivateKey issuerPrivateKey = keyStoreReader.readPrivateKey("isto videcemo")
                .orElseThrow(MissingPrivateKeyException::new);

        X509Certificate cert = CertificateGenerator.generateCertificate(subjectData, issuerData);
        keyStoreWriter.write(request.getEmail(), issuerPrivateKey, cert);
    }

    private SubjectData generateSubjectData(IssueCertificateRequest request) {
        // TODO Serijski broj sertifikata (generator baza?)
        String sn = "1";

        // klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, request.getFullName());
        builder.addRDN(BCStyle.SURNAME, request.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, request.getName());
        builder.addRDN(BCStyle.O, request.getOrganization());
        builder.addRDN(BCStyle.OU, request.getOrganizationUnitName());
        builder.addRDN(BCStyle.C, request.getCountryCode());
        builder.addRDN(BCStyle.E, request.getEmail());

        builder.addRDN(BCStyle.UID, request.getSubjectId());

        return new SubjectData(request.getPublicKey(), builder.build(), sn, request.getStartDate(),request.getEndDate());
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(2048, random);
        return keyGen.generateKeyPair();
    }

    public List<Certificate> readAllCertificates() {
        return null;
    }
    public void revokeCertificate(BigInteger serialNumber, Integer reason, String alias) throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, OperatorCreationException {
        File crlFile = new File("src/main/resources/revocationList.crl");
        byte[] fileContent = Files.readAllBytes(crlFile.toPath());

        X509v2CRLBuilder crlBuilder = new X509v2CRLBuilder(new X509CRLHolder(fileContent));

        // Enum CRLReason
        crlBuilder.addCRLEntry(serialNumber, new Date(), reason);

        JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("SHA256WithRSA");
        // contentSignerBuilder.setProvider("BC") sta je bre ovo

        //treba mi issuer
        IssuerData issuerData = keyStoreReader.readIssuerFromStore(alias);
        X509CRLHolder x509CRLHolder = crlBuilder.build(contentSignerBuilder.build(issuerData.getPrivateKey()));

        OutputStream os = new FileOutputStream("src/main/resources/revocationList.crl");
        os.write(x509CRLHolder.getEncoded());
        os.close();
    }
//    private boolean isRevoked(BigInteger serialNumber){}

}
