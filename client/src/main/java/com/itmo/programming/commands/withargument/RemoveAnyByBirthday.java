package com.itmo.programming.commands.withargument;

import com.itmo.programming.commands.Command;
import com.itmo.programming.commands.CommandResponse;
import com.itmo.programming.commands.TypeCommandResponse;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.commands.commandutils.ArgumentQuestioner;
import com.itmo.programming.console.ConsoleInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * @author Iskandarov Shakhzodbek P3133
 */

public class RemoveAnyByBirthday extends Command {
    private ArgumentQuestioner argumentQuestioner;

    public RemoveAnyByBirthday(ArgumentQuestioner argumentQuestioner) {
        super("remove_any_by_birthday", 1);
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

            LocalDateTime localDateTime = LocalDate.parse(argumentHolder.getInputParameterLine()[0], DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
            argumentHolder.setBirthday(localDateTime.toLocalDate());
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.FINE);
        } catch (DateTimeParseException e) {
            reply.add("Вы указали неверный формат даты. Введите дату дня рождения в таком формате (YYYY-MM-DD)");
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        }
    }
}
