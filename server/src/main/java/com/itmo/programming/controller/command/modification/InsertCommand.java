package com.itmo.programming.controller.command.modification;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.PersonDTO;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.service.PersonService;
import com.itmo.programming.service.UserService;

import java.io.IOException;


public class InsertCommand extends Command {
    private final PersonService personService;
    private final UserService userService;


    public InsertCommand(PersonService personService, UserService userService) {
        super("insert", ": добавляет новый элемент с заданны ключом");
        this.personService = personService;
        this.userService = userService;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        PersonDTO personDTO = argumentHolder.getInputPerson();
        personDTO.setKey(argumentHolder.getKey());
        personDTO.setUserDTO(userService.getByLogin(userDTO.getLogin()).get());
        long personId = personService.create(personDTO);
        if (personId == 0) {
            responseBody.addCommandResponseBody("Элемент c таким ключом уже существует");
        }else{
            responseBody.addCommandResponseBody("Элемент успешно добавлен в коллекцию");
        }
        return new Response(responseBody);
    }
}
