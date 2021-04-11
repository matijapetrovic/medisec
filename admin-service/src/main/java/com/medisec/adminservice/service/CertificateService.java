package com.medisec.adminservice.service;

import com.medisec.adminservice.CertificateResponse;
import com.medisec.adminservice.crypto.pki.certificates.CertificateGenerator;
import com.medisec.adminservice.crypto.pki.data.IssuerData;
import com.medisec.adminservice.crypto.pki.data.SubjectData;
import com.medisec.adminservice.crypto.pki.keystores.KeyStoreReader;
import com.medisec.adminservice.crypto.pki.keystores.KeyStoreWriter;
import com.medisec.adminservice.csr.Csr;
import com.medisec.adminservice.csr.CsrExtractor;
import com.medisec.adminservice.csr.CsrRepository;
import com.medisec.adminservice.exception.AliasNotValidException;
import com.medisec.adminservice.exception.CSRNotVerifiedException;
import com.medisec.adminservice.exception.MissingPrivateKeyException;
import com.medisec.adminservice.request.IssueCertificateRequest;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.*;

import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import java.math.BigInteger;
import java.nio.file.Files;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final KeyStoreWriter keyStoreWriter;
    private final KeyStoreReader keyStoreReader;

    private final CsrRepository csrRepository;

    public void issueCertificate(IssueCertificateRequest request) throws
            NoSuchAlgorithmException,
            CertificateException,
            OperatorCreationException,
            KeyStoreException,
            IOException,
            UnrecoverableKeyException,
            MissingPrivateKeyException,
            InvalidKeyException, CSRNotVerifiedException {
        Csr csr = csrRepository.findById(request.getCsrId()).orElseThrow(() -> new EntityNotFoundException("CSR Id invalid"));
        if (!csr.isVerified())
            throw new CSRNotVerifiedException();

        SubjectData subjectData = generateSubjectData(request, CsrExtractor.extractPK(csr.getRawCsr()));
        IssuerData issuerData = keyStoreReader.readIssuerFromStore("bongcloud");
        PrivateKey issuerPrivateKey = keyStoreReader.readPrivateKey("bongcloud")
                .orElseThrow(MissingPrivateKeyException::new);

        X509Certificate cert = CertificateGenerator.generateCertificate(subjectData, issuerData);
        keyStoreWriter.write(request.getEmail(), issuerPrivateKey, cert);
        csrRepository.delete(csr);
    }

    private SubjectData generateSubjectData(IssueCertificateRequest request, PublicKey publicKey) {
        Random rand = new Random();
        BigInteger serialNumber = new BigInteger(128, rand);

        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, request.getFullName());
        builder.addRDN(BCStyle.SURNAME, request.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, request.getGivenName());
        builder.addRDN(BCStyle.O, request.getOrganization());
        builder.addRDN(BCStyle.OU, request.getOrganizationUnitName());
        builder.addRDN(BCStyle.C, request.getCountryCode());
        builder.addRDN(BCStyle.E, request.getEmail());

        //builder.addRDN(BCStyle.UID, request.getSubjectId());

        return new SubjectData(publicKey, builder.build(), serialNumber.toString(16), request.getStartDate(),request.getEndDate());
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(2048, random);
        return keyGen.generateKeyPair();
    }

    public List<CertificateResponse> readAllCertificates() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, AliasNotValidException, CRLException {
        List<CertificateResponse> certificates = new ArrayList<>();

        Enumeration<String> aliases = keyStoreReader.getAllAliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            Certificate certificate = keyStoreReader.readCertificate(alias).orElseThrow(AliasNotValidException::new);
            boolean revoked = isRevoked(certificate);
            JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) certificate);
            CertificateResponse response = X500NameSubjectToCertificateResponse(certHolder, revoked);
            certificates.add(response);
        }

        return certificates;
    }

    private CertificateResponse X500NameSubjectToCertificateResponse(JcaX509CertificateHolder certHolder, boolean revoked) {
        X500Name subject = certHolder.getSubject();
        String cn = CsrExtractor.getField(subject, BCStyle.CN);
        String surname = CsrExtractor.getField(subject, BCStyle.SURNAME);
        String givenName = CsrExtractor.getField(subject, BCStyle.GIVENNAME);
        String o = CsrExtractor.getField(subject, BCStyle.O);
        String ou = CsrExtractor.getField(subject, BCStyle.OU);
        String c = CsrExtractor.getField(subject, BCStyle.C);
        String e = CsrExtractor.getField(subject, BCStyle.E);
        //String uid = IETFUtils.valueToString(subject.getRDNs(BCStyle.UID)[0].getFirst().getValue());

        return new CertificateResponse(certHolder.getSerialNumber().toString(16), givenName, surname, c, e, o, ou, null, certHolder.getNotBefore(), certHolder.getNotAfter(), revoked);
    }

    public void revokeCertificate(String serialNumber, Integer reason, String alias) throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, OperatorCreationException {
        File crlFile = new File("src/main/resources/revocationList.crl");
        byte[] fileContent = Files.readAllBytes(crlFile.toPath());

        X509CRLHolder hold = new X509CRLHolder(fileContent);
        X509v2CRLBuilder crlBuilder = new X509v2CRLBuilder(hold);

        // Enum CRLReason
        crlBuilder.addCRLEntry(new BigInteger(serialNumber, 16), new Date(), reason);

        IssuerData issuerData = keyStoreReader.readIssuerFromStore(alias);
        JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");

        X509CRLHolder x509CRLHolder = crlBuilder.build(contentSignerBuilder.setProvider("BC")
                                                                            .build(issuerData.getPrivateKey()));

        OutputStream os = new FileOutputStream("src/main/resources/revocationList.crl");
        os.write(x509CRLHolder.getEncoded());
        os.close();
    }
    private boolean isRevoked(Certificate certificate) throws IOException, CertificateException, CRLException {
        File crlFile = new File("src/main/resources/revocationList.crl");
        byte[] fileContent = Files.readAllBytes(crlFile.toPath());

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509CRL crl = (X509CRL)cf.generateCRL(new ByteArrayInputStream(fileContent));

        return crl.isRevoked(certificate);
    }

//    public void initRevocationList(String serialNumber, String alias) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, OperatorCreationException {
//        IssuerData issuerData = keyStoreReader.readIssuerFromStore(alias);
//        X500Name issuer = issuerData.getX500name();
//        Date now = new Date();
//        X509v2CRLBuilder builder = new X509v2CRLBuilder(issuer, now);
//        builder.addCRLEntry(new BigInteger(serialNumber), now, CRLReason.cACompromise);
//
//        JcaContentSignerBuilder contentSignerBuilder =
//                new JcaContentSignerBuilder("SHA256WithRSAEncryption");
//
//        contentSignerBuilder.setProvider("BC");
//        PrivateKey privateKey = issuerData.getPrivateKey();
//        X509CRLHolder cRLHolder =
//                builder.build(contentSignerBuilder.build(privateKey));
//
//        OutputStream os = new FileOutputStream("src/main/resources/revocationList.crl");
//        os.write(cRLHolder.getEncoded());
//        os.close();
//
//    }
}
