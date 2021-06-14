package com.medisec.hospitalservice.certificate;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.IETFUtils;

public class CertificateRequestExtractor {
    public static String getField(X500Name name, ASN1ObjectIdentifier field) {
        RDN[] rdn = name.getRDNs(field);
        if (rdn.length == 0)
            return null;
        return IETFUtils.valueToString(rdn[0].getFirst().getValue());
    }
}
