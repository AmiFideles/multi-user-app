package com.itmo.programming.controller.command.authorization;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.communication.ResponseCode;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.encryption.PasswordEncryptionException;
import com.itmo.programming.service.UserService;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class LoginCommand extends Command {
    private final UserService userService;

    public LoginCommand(UserService userService) {
        super("sign-in", "команда для атворизации");
        this.userService = userService;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) {
        ResponseBody responseBody = new ResponseBody();
        userService.getByLogin(userDTO.getLogin());
        try {
            if (userService.exists(userDTO)) {
                responseBody.addCommandResponseBody("Вы успешно авторизовались");
                return new Response(responseBody, ResponseCode.AUTHORIZED);
            }
        } catch (PasswordEncryptionException e) {
            responseBody.addCommandResponseBody(e.getMessage());
            return new Response(responseBody);
        }
        responseBody.addCommandResponseBody("Пользователь с таким логином и паролем не зарегистрирован");
        return new Response(responseBody);
    }
}
