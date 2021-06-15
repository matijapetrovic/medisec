package com.medisec.adminservice.domain.crypto.pki.keystores;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

@Component
public class KeyStoreWriter {
    private final KeyStore keyStore;

    @Value("${keystore.file}")
    private String keyStoreFile;

    // Lozinka keystorea
    @Value("${keystore.storepass}")
    private String storePass;

    @Value("${keystore.keypass}")
    private String keyPass;

    public KeyStoreWriter() throws NoSuchProviderException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        keyStore = KeyStore.getInstance("JKS", "SUN");
    }

    public void loadKeyStore() throws IOException, CertificateException, NoSuchAlgorithmException {
        if (keyStoreFile != null) {
            keyStore.load(new FileInputStream(keyStoreFile), storePass.toCharArray());
        } else {
            // Ako je cilj kreirati novi KeyStore poziva se i dalje load, pri cemu je prvi parametar null
            keyStore.load(null, storePass.toCharArray());
        }
    }

    public void write(String alias, PrivateKey privateKey, Certificate[] certificateChain) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        loadKeyStore();
        keyStore.setKeyEntry(alias, privateKey, keyPass.toCharArray(), certificateChain);
        keyStore.store(new FileOutputStream(keyStoreFile), storePass.toCharArray());
    }
}
