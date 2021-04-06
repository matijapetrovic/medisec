package com.medisec.adminservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RevokeCertificateRequest {
    Integer reason;
    String alias;
}
