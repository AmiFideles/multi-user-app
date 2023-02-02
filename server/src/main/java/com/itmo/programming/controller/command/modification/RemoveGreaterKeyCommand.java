package com.itmo.programming.controller.command.modification;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.service.PersonService;
import com.itmo.programming.service.UserService;

import java.io.IOException;

public class RemoveGreaterKeyCommand extends Command {
    private final PersonService personService;
    private final UserService userService;
    public RemoveGreaterKeyCommand(PersonService personService, UserService userService) {
        super("remove_greater_key", ": удаляет из коллекции все элементы, ключ которых превышает заданный");
        this.personService = personService;
        this.userService = userService;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        long key = argumentHolder.getKey();
        UserDTO realUserDTO = userService.getByLogin(userDTO.getLogin()).get();
        long countDeleted = personService.removeGreaterKey(key, realUserDTO.getId());
        responseBody.addCommandResponseBody(String.format("Количество удаленных элементов, ранее созданных вами  = %d", countDeleted));
        return new Response(responseBody);
    }
}
