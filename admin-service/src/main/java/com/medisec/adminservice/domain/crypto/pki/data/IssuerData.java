package com.medisec.adminservice.domain.crypto.pki.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PrivateKey;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IssuerData {
	private PrivateKey privateKey;
	private X500Name x500name;
}
