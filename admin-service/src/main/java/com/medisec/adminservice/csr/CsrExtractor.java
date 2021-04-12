package com.medisec.adminservice.csr;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequest;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class CsrExtractor {
    public static PublicKey extractPK(byte[] csr) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        PKCS10CertificationRequest request = extractCsr(csr);
        JcaPKCS10CertificationRequest jcaRequest = new JcaPKCS10CertificationRequest(request.getEncoded()).setProvider("BC");
        return jcaRequest.getPublicKey();
    }

    public static PKCS10CertificationRequest extractCsr(byte[] csr) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(csr);
        Reader pemReader = new BufferedReader(new InputStreamReader(bis));
        PEMParser pemParser = new PEMParser(pemReader);

        Object parsedObj = pemParser.readObject();

        assert parsedObj instanceof PKCS10CertificationRequest;
        return (PKCS10CertificationRequest) parsedObj;
    }

    public static String getField(X500Name name, ASN1ObjectIdentifier field) {
        RDN[] rdn = name.getRDNs(field);
        if (rdn.length == 0)
            return null;
        return IETFUtils.valueToString(rdn[0].getFirst().getValue());
    }
}
