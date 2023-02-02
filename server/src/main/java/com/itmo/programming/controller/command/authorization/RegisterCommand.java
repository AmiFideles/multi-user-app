package com.itmo.programming.controller.command.authorization;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.encryption.PasswordEncryptionException;
import com.itmo.programming.encryption.SHA512Generator;
import com.itmo.programming.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Slf4j
public class RegisterCommand extends Command {
    private final UserService userService;
    private final SHA512Generator sha512Generator;
    public  RegisterCommand(UserService userService, SHA512Generator sha512Generator) {
        super("sign-up", ": команда для регистрации пользователя");
        this.userService = userService;
        this.sha512Generator = sha512Generator;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        try {
            String salt = sha512Generator.getSalt();
            String hashedPassword = sha512Generator.getSHA512SecurePassword(userDTO.getPassword(), salt);
            userDTO.setPassword(hashedPassword);
            if (userService.create(userDTO, salt)) {
                log.info("Добавлен новый  user {}",  userDTO);
                responseBody.addCommandResponseBody("Вы успешно зарегистрировались");
            }else{
                responseBody.addCommandResponseBody("Пользователь с такими данными уже зарегистрирован");
            }
        } catch (PasswordEncryptionException e ) {
            responseBody.addCommandResponseBody(e.getMessage());
        }
        return new Response(responseBody);
    }
}
