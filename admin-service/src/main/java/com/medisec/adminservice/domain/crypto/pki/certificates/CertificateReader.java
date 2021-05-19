package com.medisec.adminservice.domain.crypto.pki.certificates;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import java.util.Iterator;

/**
 * Cita sertifikat iz fajla
 */
public class CertificateReader {

    public static final String BASE64_ENC_CERT_FILE = "./data/proba1.cer";
    public static final String BIN_ENC_CERT_FILE = "./data/proba.cer";

    public void testIt() throws IOException, CertificateException {
        System.out.println("Cita sertifikat iz Base64 formata");
        readFromBase64EncFile();
        System.out.println("\n\nCita sertifikat iz binarnog formata");
        readFromBinEncFile();
    }


    private void readFromBase64EncFile() throws IOException, CertificateException {
        FileInputStream fis = new FileInputStream(BASE64_ENC_CERT_FILE);
        BufferedInputStream bis = new BufferedInputStream(fis);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        // Cita sertifikat po sertifikat
        // Svaki certifikat je izmedju
        // -----BEGIN CERTIFICATE-----,
        // i
        // -----END CERTIFICATE-----.
        while (bis.available() > 0) {
            Certificate cert = cf.generateCertificate(bis);
            System.out.println(cert.toString());
        }
    }

    @SuppressWarnings("rawtypes")
    private void readFromBinEncFile() throws FileNotFoundException, CertificateException {
        FileInputStream fis = new FileInputStream(BIN_ENC_CERT_FILE);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // Ovde se vade svi sertifkati
        Collection c = cf.generateCertificates(fis);
        Iterator i = c.iterator();
        while (i.hasNext()) {
            Certificate cert = (Certificate) i.next();
            System.out.println(cert);
        }
    }

    public static void main(String[] args) throws IOException, CertificateException {
        CertificateReader test = new CertificateReader();
        test.testIt();
    }
}
