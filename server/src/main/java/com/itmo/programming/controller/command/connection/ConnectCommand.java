package com.itmo.programming.controller.command.connection;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.communication.ResponseCode;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;

import java.io.IOException;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ConnectCommand extends Command {
    public ConnectCommand() {
        super("connect", ": подключение к серверу");
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        responseBody.addCommandResponseBody("Вы успешно подключились к серверу");
        return new Response(responseBody, ResponseCode.CONNECTED);
    }
}
