package com.medisec.hospitalservice.certificate.keystore;

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
     * Ucitava sertifikat is KS fajla
     */
    public Optional<java.security.cert.Certificate> readCertificate(String alias) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
        keyStore.load(in, storePass.toCharArray());

        if (!keyStore.isKeyEntry(alias))
            return Optional.empty();

        java.security.cert.Certificate cert = keyStore.getCertificate(alias);
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
