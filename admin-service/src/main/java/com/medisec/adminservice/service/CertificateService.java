package com.medisec.adminservice.service;

import com.medisec.adminservice.CertificateResponse;
import com.medisec.adminservice.crypto.pki.certificates.CertificateGenerator;
import com.medisec.adminservice.crypto.pki.data.IssuerData;
import com.medisec.adminservice.crypto.pki.data.SubjectData;
import com.medisec.adminservice.crypto.pki.keystores.KeyStoreReader;
import com.medisec.adminservice.crypto.pki.keystores.KeyStoreWriter;
import com.medisec.adminservice.exception.AliasNotValidException;
import com.medisec.adminservice.exception.MissingPrivateKeyException;
import com.medisec.adminservice.request.IssueCertificateRequest;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
<<<<<<< HEAD
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
=======
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.stereotype.Service;

>>>>>>> 51963fd8a637e0f469b5949a335493f943a61803
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
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

    public List<CertificateResponse> readAllCertificates() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, AliasNotValidException {
        List<CertificateResponse> certificates = new ArrayList<>();

        Enumeration<String> aliases = keyStoreReader.getAllAliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            Certificate certificate = keyStoreReader.readCertificate(alias).orElseThrow(AliasNotValidException::new);
            JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) certificate);
            CertificateResponse response = X500NameSubjectToCertificateResponse(certHolder.getSubject());
            certificates.add(response);
        }

        return certificates;
    }

    private CertificateResponse X500NameSubjectToCertificateResponse(X500Name subject) {
        String cn = IETFUtils.valueToString(subject.getRDNs(BCStyle.CN)[0].getFirst().getValue());
        String surname = IETFUtils.valueToString(subject.getRDNs(BCStyle.SURNAME)[0].getFirst().getValue());
        String givenName = IETFUtils.valueToString(subject.getRDNs(BCStyle.GIVENNAME)[0].getFirst().getValue());
        String o = IETFUtils.valueToString(subject.getRDNs(BCStyle.O)[0].getFirst().getValue());
        String ou = IETFUtils.valueToString(subject.getRDNs(BCStyle.OU)[0].getFirst().getValue());
        String c = IETFUtils.valueToString(subject.getRDNs(BCStyle.C)[0].getFirst().getValue());
        String e = IETFUtils.valueToString(subject.getRDNs(BCStyle.E)[0].getFirst().getValue());
        String uid = IETFUtils.valueToString(subject.getRDNs(BCStyle.UID)[0].getFirst().getValue());

        // TODO: pogledaj sta za start i end date i nullove
        return new CertificateResponse(givenName, surname, c, e, o, ou, uid, null, null, null, false);
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
