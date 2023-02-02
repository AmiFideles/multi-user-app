package com.itmo.programming.commands.exceptions;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String message) {
        super(message);
    }
}
