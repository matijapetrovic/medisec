package com.medisec.hospitalservice.web.config;

import com.medisec.hospitalservice.certificate.keystore.KeyStoreReader;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.security.KeyStoreUtil;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;

import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.*;
import java.util.*;

public class SSLTrustManager implements X509TrustManager {
    private final KeyStoreReader keyStoreReader = new KeyStoreReader();

    public SSLTrustManager() throws NoSuchProviderException, KeyStoreException {
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            validation(chain);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            validation(chain);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    private void validation(X509Certificate[] x509Certificates) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        X509Certificate rootCert = (X509Certificate) keyStoreReader.readCertificate("bogncloud@gmail.com").orElseThrow();
        Set<TrustAnchor> trustAnchors = new HashSet<>();
        trustAnchors.add(new TrustAnchor(rootCert, null));

        List<X509Certificate> certChain = new ArrayList<>(Arrays.asList(x509Certificates).subList(0, x509Certificates.length - 1));

        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        CertPath certPath = certFactory.generateCertPath(certChain);

        try {
            CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX");
            PKIXParameters pkixParams = new PKIXParameters(trustAnchors);
            pkixParams.setDate(new Date());
            certPathValidator.validate(certPath, pkixParams);
        } catch (NoSuchAlgorithmException e) {
            throw new CertificateException("No such algorithm.");
        } catch (InvalidAlgorithmParameterException e) {
            throw new CertificateException("Invalid algorithm parameter.");
        } catch (CertPathValidatorException e) {
            throw new CertificateException(e.getReason().toString());
        }
    }
}
