package com.medisec.adminservice.domain.crypto.pki.certificates;

import com.medisec.adminservice.domain.crypto.pki.data.IssuerData;
import com.medisec.adminservice.domain.crypto.pki.data.SubjectData;
import com.medisec.adminservice.domain.extension.CertificateExtensions;
import com.medisec.adminservice.domain.extension.CertificateKeyUsage;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CertificateGenerator {

    public static X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, X509Certificate issuerCert, CertificateExtensions extensionsDTO) throws CertificateException, OperatorCreationException, CertIOException, NoSuchAlgorithmException {
        // Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
        // Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
        // Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");

        // Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
        builder = builder.setProvider("BC");

        // Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
        ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

        // Postavljaju se podaci za generisanje sertifiakta
        X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                new BigInteger(subjectData.getSerialNumber(), 16),
                subjectData.getStartDate(),
                subjectData.getEndDate(),
                subjectData.getX500name(),
                subjectData.getPublicKey());

        extractExtensions(certGen, issuerCert, subjectData, extensionsDTO);

        // Generise se sertifikat
        X509CertificateHolder certHolder = certGen.build(contentSigner);

        // Builder generise sertifikat kao objekat klase X509CertificateHolder
        // Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter = certConverter.setProvider("BC");

        // Konvertuje objekat u sertifikat
        return certConverter.getCertificate(certHolder);
    }

    private static void extractExtensions(X509v3CertificateBuilder certBuilder, X509Certificate issuerCert, SubjectData subjectData, CertificateExtensions extensionsDTO) throws CertIOException, NoSuchAlgorithmException, CertificateEncodingException {
        if (extensionsDTO == null) return;
        if (extensionsDTO.getBasicConstraints() != null) {
            BasicConstraints basicConstraints = extensionsDTO.getBasicConstraints().getPathLen() != null
                    ? new BasicConstraints(extensionsDTO.getBasicConstraints().getPathLen())
                    : new BasicConstraints(extensionsDTO.getBasicConstraints().isCa());
            certBuilder.addExtension(Extension.basicConstraints, extensionsDTO.getBasicConstraints().isBasicConstraintsIsCritical(), basicConstraints);
        }
        if (extensionsDTO.getKeyUsage() != null) {
            KeyUsage keyUsage = getKeyUsage(extensionsDTO.getKeyUsage());
            certBuilder.addExtension(Extension.keyUsage, extensionsDTO.getKeyUsage().isKeyUsageIsCritical(), keyUsage);
        }

        JcaX509ExtensionUtils extensionUtils = new JcaX509ExtensionUtils();
        if (extensionsDTO.isSubjectKeyId()) {
            certBuilder.addExtension(Extension.subjectKeyIdentifier, false, extensionUtils.createSubjectKeyIdentifier(subjectData.getPublicKey()));
        }

        if (extensionsDTO.isAuthorityKeyId())
            certBuilder.addExtension(Extension.authorityKeyIdentifier, false, extensionUtils.createAuthorityKeyIdentifier(issuerCert));
    }


    public static KeyUsage getKeyUsage(CertificateKeyUsage keyUsage) {
        int key = 0;
        if (keyUsage.isCrlSign())
            key |= KeyUsage.cRLSign;
        if (keyUsage.isDataEncipherment())
            key |= KeyUsage.dataEncipherment;
        if (keyUsage.isDecipherOnly())
            key |= KeyUsage.decipherOnly;
        if (keyUsage.isKeyAgreement())
            key |= KeyUsage.keyAgreement;
        if (keyUsage.isDigitalSignature())
            key |= KeyUsage.digitalSignature;
        if (keyUsage.isEncipherOnly())
            key |= KeyUsage.encipherOnly;
        if (keyUsage.isKeyCertSign())
            key |= KeyUsage.keyCertSign;
        if (keyUsage.isNonRepudiation())
            key |= KeyUsage.nonRepudiation;
        if (keyUsage.isKeyEncipherment())
            key |= KeyUsage.keyEncipherment;

        return new KeyUsage(key);
    }
}
