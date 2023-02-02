package com.itmo.programming.commands.withoutargument;

import com.itmo.programming.client.ClientRunner;
import com.itmo.programming.commands.Command;
import com.itmo.programming.commands.CommandResponse;
import com.itmo.programming.commands.TypeCommandResponse;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.console.ConsoleInterface;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ExitCommand extends Command {
    public ExitCommand(){
        super("exit", 0);
    }

    @Override
    public CommandResponse execute(ArgumentHolder argumentHolder, ConsoleInterface consoleInterface) {
        ArrayList<String> reply = new ArrayList<>();
        String verificationResult = checkCountOfArgument(argumentHolder.getCountParameter());
        if (verificationResult != null) {
            reply.add(verificationResult);
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        }
        consoleInterface.write("Завершение работы приложение");
        try {
            ClientRunner.getClientRunner().getClient().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(-1);
        return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
    }
}
