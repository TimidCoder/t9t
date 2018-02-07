package com.arvatosystems.t9t.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jpaw.util.ByteArray;

public class PasswordUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtil.class);
    static private final String CHARSET = "abcdefghijkmnopqrstuvwxyxABCDEFGHIJKLMNPQRSTUVWXYZ023456789!$&%/1O";

    /**
     * Creates a password hash for the given user and password.
     *
     * @param userName
     *            a user name
     * @param password
     *            a password
     * @return a password hash
     */
    public static ByteArray createPasswordHash(final String userName, final String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("The standard algorithm \"SHA-512\" is not available. This should never happen. It is recommended to re-install Java.");
            throw new RuntimeException(e);
        }
        md.reset();
        md.update((userName + ":" + password).getBytes());
        return new ByteArray(md.digest(password.getBytes()));
    }

    public static String generateRandomPassword(int length) {
        SecureRandom rng = new SecureRandom();
        byte[] randomBytes = new byte[length];
        rng.nextBytes(randomBytes);
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            result.append(CHARSET.charAt(randomBytes[i] & 0x3f));
        }
        return result.toString();
    }
}
