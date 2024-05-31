package com.semicolon.credeefy.utils;

import java.security.SecureRandom;

public class SecretKeyGenerator {
    public byte[] generateSecureRandomBytes() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[64];
        secureRandom.nextBytes(bytes);
        return bytes;
    }
}
