package com.itmo.programming.commands.withargument;

import com.itmo.programming.commands.Command;
import com.itmo.programming.commands.CommandResponse;
import com.itmo.programming.commands.TypeCommandResponse;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.console.ConsoleInterface;


import java.util.ArrayList;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class RemoveCommand extends Command {
    public RemoveCommand() {
        super("remove", 1);
    }

    @Override
    public CommandResponse execute(ArgumentHolder argumentHolder, ConsoleInterface consoleInterface) {
        ArrayList<String> reply = new ArrayList<>();
        String verificationResult = checkCountOfArgument(argumentHolder.getCountParameter());
        if (verificationResult != null) {
            reply.add(verificationResult);
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        }
        try {
            long key = Long.parseLong(argumentHolder.getInputParameterLine()[0]);
            argumentHolder.setKey(key);
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.FINE);
        }catch (NumberFormatException e) {
            reply.add("Вы ввели некорректное число, попробуйте исправить и запустить заново");
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        }
    }
}
