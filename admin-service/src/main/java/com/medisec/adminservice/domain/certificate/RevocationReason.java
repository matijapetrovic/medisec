package com.medisec.adminservice.domain.certificate;

import lombok.Getter;

public enum RevocationReason {
    unspecified(0, "Unspecified"),
    keyCompromise(1, "Key compromised"),
    cACompromise(2, "CA compromised"),
    affiliationChanged(3, "Affiliation changed"),
    superseded(4, "Superseded"),
    cessationOfOperation(5, "Cessation of operation"),
    certificateHold(6, "Certificate hold"),
    removeFromCRL(8, "Remove from CRL"),
    privilegeWithdrawn(9, "Privilege withdrawn"),
    aACompromise(10, "AA compromised");

    @Getter
    private final int key;
    @Getter
    private final String desc;

    RevocationReason(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
