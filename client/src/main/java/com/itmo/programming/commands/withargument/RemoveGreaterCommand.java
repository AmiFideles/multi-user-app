package com.itmo.programming.commands.withargument;

import com.itmo.programming.commands.Command;
import com.itmo.programming.commands.CommandResponse;
import com.itmo.programming.commands.TypeCommandResponse;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.commands.commandutils.ArgumentQuestioner;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.dto.PersonDTO;

import java.util.ArrayList;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class RemoveGreaterCommand extends Command {
    private final ArgumentQuestioner argumentQuestioner;

    public RemoveGreaterCommand(ArgumentQuestioner argumentQuestioner) {
        super("remove_greater", 0);
        this.argumentQuestioner = argumentQuestioner;
    }

    @Override
    public CommandResponse execute(ArgumentHolder argumentHolder, ConsoleInterface consoleInterface) {
        ArrayList<String> reply = new ArrayList<>();
        String verificationResult = checkCountOfArgument(argumentHolder.getCountParameter());
        if (verificationResult != null) {
            reply.add(verificationResult);
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        }
        PersonDTO personDTO = argumentQuestioner.readPersonDTO(consoleInterface);
        argumentHolder.setInputPerson(personDTO);
        return new CommandResponse(argumentHolder, reply, TypeCommandResponse.FINE);
    }
}
