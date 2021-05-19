package com.medisec.adminservice.web.certificate;

import lombok.Data;

@Data
public class RevocationReasonDTO {
    private final int key;
    private final String desc;
}
