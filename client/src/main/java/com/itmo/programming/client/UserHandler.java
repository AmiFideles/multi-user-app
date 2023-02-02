package com.itmo.programming.client;

import com.itmo.programming.commands.Command;
import com.itmo.programming.commands.CommandResponse;
import com.itmo.programming.commands.CommandHolder;
import com.itmo.programming.commands.exceptions.NoSuchCommandException;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.console.ConsoleInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Getter
@Setter
public class UserHandler {
    private final CommandHolder commandHolder;
    private boolean authorized = false;

    public UserHandler(CommandHolder commandHolder) {
        this.commandHolder = commandHolder;
    }

    public CommandResponse execute(String inputLine, ConsoleInterface consoleInterface) {
        String[] args = simplifyIncomingLine(inputLine);
        checkPermission(args);
        Command command = commandHolder.getCommand(args[0]);
        command.setName(args[0]);
        CommandResponse commandResponse = command.execute(new ArgumentHolder(prepareParameters(args)), consoleInterface);
        commandResponse.setCommandName(args[0]);
        return commandResponse;
    }
    //TODO я знаю, что это говно. Надо нормально придумать права для команд в зависимости от статуса авторизации
    private void checkPermission(String[] args) {
        if (!authorized) {
            if (!(args[0].equals("sign-up") || args[0].equals("sign-in") || args[0].equals("exit"))) {
                throw new NoSuchCommandException("Вам не доступна такая команда\n" +
                        "Вы можете зарегистрироваться с помощью команды sign-up или войти с помощью команда sign-in");
            }
        } else {
            if (args[0].equals("sign-up") || args[0].equals("sign-in")) {
                throw new NoSuchCommandException("Вам не доступна такая команда. Воспользуйтесь help");
            }
        }
    }

    private String[] simplifyIncomingLine(String line) {
        if (line == null) {
            throw new NoSuchCommandException("Команды  не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды");
        }
        if (line.isEmpty() || line.trim().isEmpty()) {
            throw new NoSuchCommandException("Команды  не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды");
        }
        return line.trim().replaceAll("\\s+", " ").split(" ");
    }

    private String[] prepareParameters(String[] arr) {
        return Arrays.copyOfRange(arr, 1, arr.length);
    }


}
