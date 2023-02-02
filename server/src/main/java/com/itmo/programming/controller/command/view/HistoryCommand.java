package com.itmo.programming.controller.command.view;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class HistoryCommand extends Command {
    private ConcurrentHashMap<UserDTO, List<String>> history;
    public HistoryCommand() {
        super("history", ": выводит команды, введенные пользователем (без их аргументов)");
        this.history = new ConcurrentHashMap<>();
    }

    public void addToCommandList(UserDTO userDTO, String commandName) {
        if (!(commandName.equals("connect") || commandName.equals("sign-in") || commandName.equals("sign-up"))){
            List<String> historyList = history.get(userDTO);
            if (historyList==null){
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(commandName);
                history.put(userDTO, arrayList);
            }else{
                historyList.add(commandName);
            }
        }
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        responseBody.addCommandResponseBody(String.join("\n", history.get(userDTO)));
        return new Response(responseBody);
    }
}
