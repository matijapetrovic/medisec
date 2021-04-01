package com.medisec.adminservice.crypto.pki.keystores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class KeyStoreWriter {
    // KeyStore je Java klasa za citanje specijalizovanih datoteka koje se koriste za cuvanje kljuceva
    // Tri tipa entiteta koji se obicno nalaze u ovakvim datotekama su:
    // - Sertifikati koji ukljucuju javni kljuc
    // - Privatni kljucevi
    // - Tajni kljucevi, koji se koriste u simetricnima siframa
    private KeyStore keyStore;


    public KeyStoreWriter() throws NoSuchProviderException, KeyStoreException {
        keyStore = KeyStore.getInstance("JKS", "SUN");
    }

    public void loadKeyStore(String fileName, char[] password) throws IOException, CertificateException, NoSuchAlgorithmException {
        if (fileName != null) {
            keyStore.load(new FileInputStream(fileName), password);
        } else {
            // Ako je cilj kreirati novi KeyStore poziva se i dalje load, pri cemu je prvi parametar null
            keyStore.load(null, password);
        }
    }

    public void saveKeyStore(String fileName, char[] password) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        keyStore.store(new FileOutputStream(fileName), password);
    }

    public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) throws KeyStoreException {
        keyStore.setKeyEntry(alias, privateKey, password, new Certificate[]{certificate});
    }
}
