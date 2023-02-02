package com.itmo.programming.controller.command.modification;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.service.PersonService;
import com.itmo.programming.service.UserService;

import java.io.IOException;


public class RemoveCommand extends Command {
    private final PersonService personService;
    private final UserService userService;

    public RemoveCommand(PersonService personService, UserService userService) {
        super("remove", ": удалить элемент из коллекции по его ключу");
        this.personService = personService;
        this.userService = userService;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        UserDTO realUserDTO = userService.getByLogin(userDTO.getLogin()).get();
        long key = argumentHolder.getKey();
        if (personService.removeByKeyAndUserId(key, realUserDTO.getId())) {
            responseBody.addCommandResponseBody("Элемент с данным ключом успешно удален");
        } else {
            responseBody.addCommandResponseBody("Вы не можете удалить элемент с таким ключом");
        }
        return new Response(responseBody);
    }
}
