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
public class UpdateCommand extends Command {
    private ArgumentQuestioner argumentQuestioner;

    public UpdateCommand(ArgumentQuestioner argumentQuestioner) {
        super("update",1);
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
        try {
            long id = Long.parseLong(argumentHolder.getInputParameterLine()[0]);
            PersonDTO personDTO = argumentQuestioner.readPersonDTO(consoleInterface);
            argumentHolder.setId(id);
            argumentHolder.setInputPerson(personDTO);
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.FINE);
        } catch (NumberFormatException e) {
            reply.add("Вы ввели некорректное число, попробуйте исправить и запустить заново");
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        }

    }
}
