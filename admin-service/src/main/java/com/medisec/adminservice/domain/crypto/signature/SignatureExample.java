package com.medisec.adminservice.domain.crypto.signature;


import com.medisec.adminservice.util.Base64Utility;

import java.security.*;


/**
 * Generise i proverava digitalni potpis
 */
public class SignatureExample {

    public void testIt() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException {
        PublicKey publicKey;
        PrivateKey privateKey;
        String data = "Ovo su podaci koji se digitalno potpisuju";

        System.out.println("===== Primer digitalnog potpisivanja =====");
        System.out.println("Podaci koji se potpisuju: " + data);
        byte[] dataToSign = data.getBytes();

        System.out.println("\n===== Generisanje kljuceva =====");
        KeyPair kp = generateKeys();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
        System.out.println("Generisan javni kljuc: " + Base64Utility.encode(publicKey.getEncoded()));
        System.out.println("Generisan privatni kljuc: " + Base64Utility.encode(privateKey.getEncoded()));

        System.out.println("\n===== Potpisivanje privatnim kljucem =====");
        byte[] signature = sign(dataToSign, privateKey);
        System.out.println("Potpis: " + Base64Utility.encode(signature));

        System.out.println("\n===== Provera potpisa upotrebom javnog kljuca =====");
        boolean valid = verify(dataToSign, signature, publicKey);
        if (valid) {
            System.out.println("\nPotpis je ispravan :)");
        } else {
            System.out.println("\nPotpis nije ispravan, doslo je do gubitka integriteta :(");
        }


        System.out.println("\n===== Provera potpisa nakon izmene potpisanih podataka =====");
        dataToSign[0] = (byte) 0xFA;
        valid = verify(dataToSign, signature, publicKey);
        if (valid) {
            System.out.println("\nPotpis je ispravan :)");
        } else {
            System.out.println("\nPotpis nije ispravan, doslo je do gubitka integriteta :(");
        }
    }

    private KeyPair generateKeys() throws NoSuchProviderException, NoSuchAlgorithmException {
        // Generator para kljuceva
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        // Za kreiranje kljuceva neophodno je definisati generator pseudoslucajnih brojeva
        // Ovaj generator mora biti bezbedan (nije jednostavno predvideti koje brojeve ce RNG generisati)
        // U ovom primeru se koristi generator zasnovan na SHA1 algoritmu, gde je SUN provajder
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

        // inicijalizacija generatora, 2048 bitni kljuc
        keyGen.initialize(2048, random);

        // generise par kljuceva koji se sastoji od javnog i privatnog kljuca
        return keyGen.generateKeyPair();
    }

    private byte[] sign(byte[] data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        // Kreiranje objekta koji nudi funkcionalnost digitalnog potpisivanja
        // Prilikom getInstance poziva prosledjujemo algoritam koji cemo koristiti
        // U ovom slucaju cemo generisati SHA-1 hes kod koji cemo potpisati upotrebom RSA asimetricne sifre
        Signature sig = Signature.getInstance("SHA1withRSA");

        // Navodimo kljuc kojim potpisujemo
        sig.initSign(privateKey);

        // Postavljamo podatke koje potpisujemo
        sig.update(data);

        // Vrsimo potpisivanje
        return sig.sign();
    }

    private boolean verify(byte[] data, byte[] signature, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        // Kreiranje objekta koji nudi funkcionalnost digitalnog potpisivanja
        // Prilikom getInstance poziva prosledjujemo algoritam koji cemo koristiti
        // U ovom slucaju cemo generisati SHA-1 hes kod koji cemo potpisati upotrebom RSA asimetricne sifre
        Signature sig = Signature.getInstance("SHA1withRSA");

        // Navodimo kljuc sa kojim proveravamo potpis
        sig.initVerify(publicKey);

        // Postavljamo podatke koje potpisujemo
        sig.update(data);

        // Vrsimo proveru digitalnog potpisa
        return sig.verify(signature);
    }

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        SignatureExample sig = new SignatureExample();
        sig.testIt();
    }
}
