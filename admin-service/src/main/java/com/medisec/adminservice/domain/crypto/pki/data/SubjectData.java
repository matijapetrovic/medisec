package com.medisec.adminservice.crypto.pki.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PublicKey;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectData {

	private PublicKey publicKey;
	private X500Name x500name;
	private String serialNumber;
	private Date startDate;
	private Date endDate;

}
