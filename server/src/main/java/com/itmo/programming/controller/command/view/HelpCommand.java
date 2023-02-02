package com.itmo.programming.controller.command.view;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HelpCommand extends Command {
    private HashMap<String, String> helpManual;

    public HelpCommand() {
        super("help", ": вывести справку по доступным командам");
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        List<String> tempList = new ArrayList<>();
        helpManual.forEach((key, value) -> tempList.add(key + value));
        responseBody.addCommandResponseBody(String.join("\n", tempList));
        return new Response(responseBody);
    }

    public void setHelpManual(HashMap<String, String> helpManual) {
        this.helpManual = helpManual;
    }
}
