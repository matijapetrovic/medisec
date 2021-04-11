package com.medisec.adminservice.util;

import java.math.BigInteger;
import java.util.Random;

public class SerialNumberGenerator {
    private static final String MAX_LIMIT = "5000000000000";
    private static final String MIN_LIMIT =  "25000000000";

    private static BigInteger generateSerialNumberBigInt() {
        BigInteger maxLimit = new BigInteger(MAX_LIMIT);
        BigInteger minLimit = new BigInteger(MIN_LIMIT);
        BigInteger bigInteger = maxLimit.subtract(minLimit);

        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger res = new BigInteger(len, randNum);
        if (res.compareTo(minLimit) < 0)
            res = res.add(minLimit);
        if (res.compareTo(bigInteger) >= 0)
            res = res.mod(bigInteger).add(minLimit);

        return res;
    }

    public static String generateSerialNumber() {
        return generateSerialNumberBigInt().toString();
    }
}
