package com.itmo.programming.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class SHA512Generator {

    public String getSalt() throws PasswordEncryptionException {
        // Always use a SecureRandom generator
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new PasswordEncryptionException("Произошла ошибка при шифровании пароля", e);
        }

        // Create array for salt
        byte[] salt = new byte[16];

        // Get a random salt
        sr.nextBytes(salt);

        // return salt
        return salt.toString();
    }

    public String getSHA512SecurePassword(String passwordToHash, String salt) throws PasswordEncryptionException {
        String generatedPassword = null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new PasswordEncryptionException("Произошла ошибка во время шифорования", e);
        }
        md.update(salt.getBytes());
        byte[] bytes = md.digest(passwordToHash.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        generatedPassword = sb.toString();

        return generatedPassword;
    }
}
