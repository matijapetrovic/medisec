package com.medisec.hospitalservice.keypairgenerator;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class KeyPairGenerator {
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        java.security.KeyPairGenerator keyGen = java.security.KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(2048, random);
        return keyGen.generateKeyPair();
    }
}
