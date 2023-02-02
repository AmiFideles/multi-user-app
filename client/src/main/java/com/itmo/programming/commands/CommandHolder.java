package com.itmo.programming.commands;

import com.itmo.programming.commands.commandutils.ArgumentQuestioner;
import com.itmo.programming.commands.exceptions.NoSuchCommandException;
import com.itmo.programming.commands.login.SignInCommand;
import com.itmo.programming.commands.login.SignUpCommand;
import com.itmo.programming.commands.withargument.*;
import com.itmo.programming.commands.withoutargument.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class CommandHolder {
    private final Map<String, Command> allCommand = new HashMap<>();
    private final ArgumentQuestioner argumentQuestioner;

    public CommandHolder() {
        this.argumentQuestioner = new ArgumentQuestioner();
        initializeClientCommand();
    }

    public Command getCommand(String nameCommand) throws NoSuchCommandException {
        if (allCommand.containsKey(nameCommand)) {
            return allCommand.get(nameCommand);
        } else {
            throw new NoSuchCommandException(String.format("Команды %s не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды", nameCommand));
        }
    }

    private void initializeClientCommand() {
        List<Command> commandList = Stream.of(new ClearCommand(), new HelpCommand(), new HistoryCommand(), new InfoCommand(), new PrintAscendingCommand(), new ShowCommand(), new CountLessThanLocation(argumentQuestioner), new ExecuteScriptCommand(), new InsertCommand(argumentQuestioner), new RemoveAnyByBirthday(argumentQuestioner), new RemoveCommand(), new RemoveGreaterCommand(argumentQuestioner), new RemoveGreaterKeyCommand(), new UpdateCommand(argumentQuestioner), new ExitCommand(), new SignUpCommand(argumentQuestioner), new SignInCommand(argumentQuestioner)).collect(Collectors.toList());
        convertToMap(commandList);
    }

    private void convertToMap(List<Command> commands) {
        commands.forEach(x -> allCommand.put(x.getName(), x));
    }

}
