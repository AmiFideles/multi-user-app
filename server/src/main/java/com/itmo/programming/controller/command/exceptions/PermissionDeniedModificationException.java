package com.itmo.programming.controller.command.exceptions;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class PermissionDeniedModificationException extends Exception{
    //TODO очень плохо. Поменять когда буду делать локализацию и интернализацию
    private final String message = "Вы не можете модифицировать данный элемент";

    @Override
    public String getMessage() {
        return message;
    }
}
