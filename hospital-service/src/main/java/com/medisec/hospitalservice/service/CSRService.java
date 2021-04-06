package com.medisec.hospitalservice.service;

import com.medisec.hospitalservice.request.CertificateSigningRequest;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequestHolder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import javax.security.auth.x500.X500Principal;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.*;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CSRService {
    @Value("${admin-service.certificate-signing-request-url}")
    private String requestUrl;


    public void sendCSR(CertificateSigningRequest certificateSigningRequest) throws NoSuchProviderException, NoSuchAlgorithmException, OperatorCreationException, IOException {
        KeyPair keyPair = generateKeyPair();

        HashMap<String, String> subjectData = new HashMap<>();

        subjectData.put("CN", certificateSigningRequest.getCommonName());
        subjectData.put("SURNAME", certificateSigningRequest.getSurname());
        subjectData.put("GIVENNAME", certificateSigningRequest.getGivenName());
        subjectData.put("O", certificateSigningRequest.getOrganization());
        subjectData.put("OU", certificateSigningRequest.getOrganizationUnit());
        subjectData.put("C", certificateSigningRequest.getCountry());
        subjectData.put("email", certificateSigningRequest.getEmail());
        // ? ? ?    subjectData.put("serialNumber", csr.getSerialNumber());

        String mapAsString = subjectData.keySet().stream()
                .filter(key -> subjectData.get(key) != null && !subjectData.get(key).isEmpty())
                .map(key -> key + "=" + subjectData.get(key))
                .collect(Collectors.joining(","));


        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
                new X500Principal(mapAsString), keyPair.getPublic());
        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder("SHA256withRSA");
        ContentSigner signer = csBuilder.build(keyPair.getPrivate());
        PKCS10CertificationRequestHolder csr = p10Builder.build(signer);

        //TODO save private key
        //System.out.println("Private key: " + Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));

        StringBuilder builder = new StringBuilder();

        builder.append("-----BEGIN CERTIFICATE REQUEST-----\n");
        builder.append(DatatypeConverter.printBase64Binary(csr.getEncoded()));
        builder.append("\n-----END CERTIFICATE REQUEST-----");

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(builder.toString());

        HttpStatus httpStatus = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.POST,
                    request,
                    String.class).getStatusCode();
    }


    private KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(2048, random);
        return keyGen.generateKeyPair();
    }
}
