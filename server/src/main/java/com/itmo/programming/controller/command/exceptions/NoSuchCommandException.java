package com.itmo.programming.controller.command.exceptions;

/**
 * Исключение, обозначающее не существующую команду
 */
public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String message) {
        super(message);
    }
}
