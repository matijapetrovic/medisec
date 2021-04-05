package com.medisec.adminservice.crypto.pki.keystores;

import com.medisec.adminservice.CertificateResponse;
import com.medisec.adminservice.crypto.pki.data.IssuerData;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Component
public class KeyStoreReader {
    private final KeyStore keyStore;

    @Value("${keystore.file}")
    private String keyStoreFile;

    // Lozinka keystorea
    @Value("${keystore.storepass}")
    private String storePass;

    // Lozinka privatnog kljuca issuera
    @Value("${keystore.keypass}")
    private String keyPass;


    public KeyStoreReader() throws NoSuchProviderException, KeyStoreException {
            keyStore = KeyStore.getInstance("JKS", "SUN");
    }

    /**
     * Zadatak ove funkcije jeste da ucita podatke o izdavaocu i odgovarajuci privatni kljuc.
     * Ovi podaci se mogu iskoristiti da se novi sertifikati izdaju.
     *
     * @param alias        - alias putem kog se identifikuje sertifikat izdavaoca
     * @return - podatke o izdavaocu i odgovarajuci privatni kljuc
     */
    public IssuerData readIssuerFromStore(String alias) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
        keyStore.load(in, storePass.toCharArray());

        Certificate cert = keyStore.getCertificate(alias);
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, keyPass.toCharArray());

        X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
        return new IssuerData(privateKey, issuerName);
    }

    /**
     * Ucitava sertifikat is KS fajla
     */
    public Optional<Certificate> readCertificate(String alias) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
        keyStore.load(in, storePass.toCharArray());

        if (!keyStore.isKeyEntry(alias))
            return Optional.empty();

        Certificate cert = keyStore.getCertificate(alias);
        return Optional.of(cert);
    }

    /**
     * Ucitava privatni kljuc is KS fajla
     */
    public Optional<PrivateKey> readPrivateKey(String alias) throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
        keyStore.load(in, storePass.toCharArray());

        if (!keyStore.isKeyEntry(alias))
            return Optional.empty();

        PrivateKey pk = (PrivateKey) keyStore.getKey(alias, keyPass.toCharArray());
        return Optional.of(pk);
    }

    public List<CertificateResponse> readAllCertificates() {

        return null;
    }

    public Certificate[] readCertificateChain(String alias) throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
        keyStore.load(in, storePass.toCharArray());

        if (!keyStore.isKeyEntry(alias)) {
            return null;
        }
        return keyStore.getCertificateChain(alias);
    }

    public Enumeration<String> getAllAliases() throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
        keyStore.load(in, storePass.toCharArray());

        return keyStore.aliases();
    }
}
