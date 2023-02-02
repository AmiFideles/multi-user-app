package com.itmo.programming.controller.command.modification;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.exceptions.PermissionDeniedModificationException;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.mapper.toentity.PersonDTOMapper;
import com.itmo.programming.service.PersonService;
import com.itmo.programming.service.UserService;

import java.io.IOException;


public class UpdateCommand extends Command {
    private final PersonService personService;
    private final UserService userService;

    public UpdateCommand(PersonService personService, UserService userService) {
        super("update", ": обновляет значение элемента коллекции, id которого равен заданному");
        this.personService = personService;
        this.userService = userService;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        long userId = userService.getByLogin(userDTO.getLogin()).get().getId();
        long id = argumentHolder.getId();
        try {
            if (!personService.update(id, PersonDTOMapper.INSTANCE.toEntity(argumentHolder.getInputPerson()), userId)) {
                responseBody.addCommandResponseBody("Элемент с таким ID отсутсвует");
                responseBody.addCommandResponseBody("Элемент успешно изменен");
            }
            responseBody.addCommandResponseBody("Элемент успешно изменен");
        } catch (PermissionDeniedModificationException e) {
            responseBody.addCommandResponseBody(e.getMessage());
        }
        return new Response(responseBody);
    }
}
