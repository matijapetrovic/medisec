package com.medisec.adminservice.crypto.pki.keystores;

import com.medisec.adminservice.crypto.pki.data.IssuerData;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Optional;

public class KeyStoreReader {
    // KeyStore je Java klasa za citanje specijalizovanih datoteka koje se koriste za cuvanje kljuceva
    // Tri tipa entiteta koji se obicno nalaze u ovakvim datotekama su:
    // - Sertifikati koji ukljucuju javni kljuc
    // - Privatni kljucevi
    // - Tajni kljucevi, koji se koriste u simetricnima siframa
    private KeyStore keyStore;

    public KeyStoreReader() throws NoSuchProviderException, KeyStoreException {
            keyStore = KeyStore.getInstance("JKS", "SUN");
    }

    /**
     * Zadatak ove funkcije jeste da ucita podatke o izdavaocu i odgovarajuci privatni kljuc.
     * Ovi podaci se mogu iskoristiti da se novi sertifikati izdaju.
     *
     * @param keyStoreFile - datoteka odakle se citaju podaci
     * @param alias        - alias putem kog se identifikuje sertifikat izdavaoca
     * @param password     - lozinka koja je neophodna da se otvori key store
     * @param keyPass      - lozinka koja je neophodna da se izvuce privatni kljuc
     * @return - podatke o izdavaocu i odgovarajuci privatni kljuc
     */
    public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        // Datoteka se ucitava
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
        keyStore.load(in, password);

        // Iscitava se sertifikat koji ima dati alias
        Certificate cert = keyStore.getCertificate(alias);

        // Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
        PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);

        X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
        return new IssuerData(issuerName, privKey);
    }

    /**
     * Ucitava sertifikat is KS fajla
     */
    public Optional<Certificate> readCertificate(String keyStoreFile, String keyStorePass, String alias) throws NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        // kreiramo instancu KeyStore
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");
        // ucitavamo podatke
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
        ks.load(in, keyStorePass.toCharArray());

        if (ks.isKeyEntry(alias)) {
            Certificate cert = ks.getCertificate(alias);
            return Optional.of(cert);
        }

        return Optional.empty();
    }

    /**
     * Ucitava privatni kljuc is KS fajla
     */
    public Optional<PrivateKey> readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) throws NoSuchProviderException, KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException {
        // kreiramo instancu KeyStore
        KeyStore ks = KeyStore.getInstance("JKS", "SUN");
        // ucitavamo podatke
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
        ks.load(in, keyStorePass.toCharArray());

        if (ks.isKeyEntry(alias)) {
            PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
            return Optional.of(pk);
        }

        return Optional.empty();
    }
}
