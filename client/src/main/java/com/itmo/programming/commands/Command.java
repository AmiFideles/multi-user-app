package com.itmo.programming.commands;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.console.ConsoleInterface;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Getter @Setter
public abstract class Command {
    private final int countOfArguments;
    private String name;
    private String description;


    public Command(String name, int countOfArguments) {
        this.name = name;
        this.countOfArguments = countOfArguments;
    }

    public String checkCountOfArgument(int countOfArguments) {
        if (this.countOfArguments != countOfArguments) {
            return "Неверное количество аргументов:" + "Пришло " + countOfArguments + " ожидалось " + this.countOfArguments;
        } else return null;
    }

    public abstract CommandResponse execute(ArgumentHolder argumentHolder, ConsoleInterface consoleInterface);

}
