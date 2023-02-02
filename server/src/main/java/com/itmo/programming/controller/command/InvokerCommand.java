package com.itmo.programming.controller.command;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.controller.command.view.HistoryCommand;
import com.itmo.programming.dto.UserDTO;

import java.io.IOException;

/**
 * Через данный класс происходит выполнение команды по полученной строке от пользователя.
 * Класс вычисляет команду и вызывает соответствующий метод для ее выполнения
 */
public class InvokerCommand {
    private final CommandManager commandManager;
    private final HistoryCommand historyCommand;

    public InvokerCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.historyCommand = (HistoryCommand) commandManager.getCommand("history");
    }

    /**
     * Метод в котором выполняются команды по введенной строке пользователя
     */
    public Response execute(String commandName, ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        historyCommand.addToCommandList(userDTO, commandName);
        Command command = commandManager.getCommand(commandName);
        return command.execute(argumentHolder, userDTO);
    }
}
