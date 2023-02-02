package com.itmo.programming.commands.withoutargument;

import com.itmo.programming.commands.Command;
import com.itmo.programming.commands.CommandResponse;
import com.itmo.programming.commands.TypeCommandResponse;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.console.ConsoleInterface;

import java.util.ArrayList;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear",0);
    }

    @Override
    public CommandResponse execute(ArgumentHolder argumentHolder, ConsoleInterface consoleInterface) {
        ArrayList<String> reply = new ArrayList<>();
        String verificationResult = checkCountOfArgument(argumentHolder.getCountParameter());
        if (verificationResult != null) {
            reply.add(verificationResult);
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        }
        return new CommandResponse(argumentHolder,reply, TypeCommandResponse.FINE);


    }
}
