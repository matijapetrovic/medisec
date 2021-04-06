package com.medisec.adminservice.csr;

import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class CsrExtractor {
    public static PublicKey extractPK(Csr csr) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] rawCsr = csr.getRawCsr();
        ByteArrayInputStream bis = new ByteArrayInputStream(rawCsr);
        Reader pemReader = new BufferedReader(new InputStreamReader(bis));
        PEMParser pemParser = new PEMParser(pemReader);

        Object parsedObj = pemParser.readObject();

        assert parsedObj instanceof PKCS10CertificationRequest;
        PKCS10CertificationRequest request = (PKCS10CertificationRequest) parsedObj;
        JcaPKCS10CertificationRequest jcaRequest = new JcaPKCS10CertificationRequest(request.getEncoded()).setProvider("BC");
        return jcaRequest.getPublicKey();
    }
}
