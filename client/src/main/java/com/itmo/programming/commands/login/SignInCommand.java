package com.itmo.programming.commands.login;

import com.itmo.programming.commands.Command;
import com.itmo.programming.commands.CommandResponse;
import com.itmo.programming.commands.TypeCommandResponse;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.commands.commandutils.ArgumentQuestioner;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.dto.UserDTO;

import java.util.ArrayList;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class SignInCommand extends Command {
    private ArgumentQuestioner argumentQuestioner;
    public SignInCommand(ArgumentQuestioner argumentQuestioner) {
        super("sign-in", 0);
        this.argumentQuestioner = argumentQuestioner;
    }

    @Override
    public CommandResponse execute(ArgumentHolder argumentHolder, ConsoleInterface consoleInterface) {
        UserDTO userDTO = argumentQuestioner.readUserDTO(consoleInterface);
        return new CommandResponse(userDTO, new ArrayList<String>(),argumentHolder, TypeCommandResponse.FINE);
    }
}
