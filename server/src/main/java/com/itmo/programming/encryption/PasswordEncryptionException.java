package com.itmo.programming.encryption;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class PasswordEncryptionException extends Exception{
    public PasswordEncryptionException(String message, Throwable throwable){
        super(message, throwable);
    }
}
