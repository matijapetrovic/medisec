package com.medisec.adminservice.domain.certificate;

import com.medisec.adminservice.domain.crypto.pki.certificates.CertificateGenerator;
import com.medisec.adminservice.domain.crypto.pki.data.IssuerData;
import com.medisec.adminservice.domain.crypto.pki.data.SubjectData;
import com.medisec.adminservice.domain.crypto.pki.keystores.KeyStoreReader;
import com.medisec.adminservice.domain.crypto.pki.keystores.KeyStoreWriter;
import com.medisec.adminservice.domain.certificate_request.CertificateSigningRequest;
import com.medisec.adminservice.domain.certificate_request.CertificateRequestExtractor;
import com.medisec.adminservice.domain.certificate_request.CertificateRequestRepository;
import com.medisec.adminservice.exception.AliasNotValidException;
import com.medisec.adminservice.exception.CSRNotVerifiedException;
import com.medisec.adminservice.exception.MissingPrivateKeyException;
import com.medisec.adminservice.web.certificate.IssueCertificateRequest;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
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

import static com.medisec.adminservice.domain.crypto.KeyPairGenerator.generateKeyPair;

@Service
@RequiredArgsConstructor
public class CertificateService {
    private final KeyStoreWriter keyStoreWriter;
    private final KeyStoreReader keyStoreReader;

    private final CertificateRequestRepository certificateRequestRepository;

    public void issueCertificate(IssueCertificateRequest request) throws
            NoSuchAlgorithmException,
            CertificateException,
            OperatorCreationException,
            KeyStoreException,
            IOException,
            UnrecoverableKeyException,
            InvalidKeyException, NoSuchProviderException {
        Certificate[] issuerChain = keyStoreReader.readCertificateChain(request.getIssuerAlias());
        if (issuerChain == null)
            throw new AliasNotValidException();
        PublicKey subjectPublicKey = getSubjectPublicKey(request.getCsrId());
        SubjectData subjectData = new SubjectDataBuilder(subjectPublicKey, request.getStartDate(), request.getEndDate())
                .withSubjectId(request.getSubjectData().getSubjectId())
                .withCommonName(request.getSubjectData().getFullName())
                .withCountryCode(request.getSubjectData().getCountryCode())
                .withEmail(request.getSubjectData().getEmail())
                .withGivenName(request.getSubjectData().getGivenName())
                .withOrganization(request.getSubjectData().getOrganization())
                .withOrganizationUnit(request.getSubjectData().getOrganizationUnitName())
                .withSurname(request.getSubjectData().getSurname())
                .build();

        IssuerData issuerData =  keyStoreReader.readIssuerFromStore(request.getIssuerAlias());
        PrivateKey issuerPrivateKey = keyStoreReader.readPrivateKey(request.getIssuerAlias())
                .orElseThrow(MissingPrivateKeyException::new);
       
        X509Certificate cert = CertificateGenerator.generateCertificate(subjectData, issuerData, (X509Certificate)issuerChain[0], request.getExtensions());
        List<Certificate> newChain = new LinkedList<>(Arrays.asList(issuerChain));
        newChain.add(0, cert);

        keyStoreWriter.write(request.getSubjectData().getEmail(), issuerPrivateKey, newChain.toArray(new Certificate[0]));
    }

    private PublicKey getSubjectPublicKey(Long csrId) throws NoSuchProviderException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        if (csrId == null) {
            return generateKeyPair().getPublic();
        }
        CertificateSigningRequest certificateSigningRequest = certificateRequestRepository.findById(csrId).orElseThrow(() -> new EntityNotFoundException("CSR Id invalid"));
        if (!certificateSigningRequest.isVerified())
            throw new CSRNotVerifiedException();
        PublicKey subjectPublicKey = CertificateRequestExtractor.extractPK(certificateSigningRequest.getRawCsr());
        certificateRequestRepository.deleteById(csrId);
        return subjectPublicKey;
    }

    public List<CertificateResponse> readAllCertificates() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, AliasNotValidException, CRLException {
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

    public List<String> getIssuerAliases() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        List<String> issuerAliases = new ArrayList<>();

        Enumeration<String> aliases = keyStoreReader.getAllAliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            Optional<Certificate> certificateOptional = keyStoreReader.readCertificate(alias);
            if (certificateOptional.isEmpty()) continue;
            X509Certificate certificate = (X509Certificate) certificateOptional.get();
            if (certificate.getBasicConstraints() != -1)
                issuerAliases.add(alias);
        }

        return issuerAliases;
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

    public boolean isValid(X509Certificate certificate) throws CertificateException, CRLException, IOException {
        try {
            certificate.checkValidity();
        } catch (CertificateNotYetValidException | CertificateExpiredException e) {
            return false;
        }
        return !isRevoked(certificate);
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
