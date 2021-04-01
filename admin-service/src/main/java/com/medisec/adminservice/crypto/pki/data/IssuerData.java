package com.medisec.adminservice.crypto.pki.data;

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

	private X500Name x500name;
	private PrivateKey privateKey;

}
