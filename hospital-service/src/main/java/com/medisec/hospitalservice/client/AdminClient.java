package com.medisec.hospitalservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="admin-client",url="https://localhost:8480/api")
public interface AdminClient {
    @RequestMapping(method=RequestMethod.POST, value="/certificate-requests")
    void createCertificateRequest(byte[] csr);

    @RequestMapping(method = RequestMethod.POST, value = "/certificates/{sn}/pending-revoke")
    void revokeCertificate(byte[] csr, @PathVariable(value = "sn") String sn);

    @RequestMapping(method=RequestMethod.POST, value = "/certificates/is-revoked")
    boolean isCertificateRevoked(byte[] certificate);
}
