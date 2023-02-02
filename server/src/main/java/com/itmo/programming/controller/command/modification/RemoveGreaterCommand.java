package com.itmo.programming.controller.command.modification;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.mapper.toentity.PersonDTOMapper;
import com.itmo.programming.model.Person;
import com.itmo.programming.service.PersonService;
import com.itmo.programming.service.UserService;

import java.io.IOException;


public class RemoveGreaterCommand extends Command {
    private final PersonService personService;
    private final UserService userService;

    public RemoveGreaterCommand(PersonService personService, UserService userService) {
        super("remove_greater", ": удаляет все элементы из коллекции, превышающие заданный");
        this.personService = personService;
        this.userService = userService;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        Person person = PersonDTOMapper.INSTANCE.toEntity(argumentHolder.getInputPerson());
        UserDTO realUserDTO = userService.getByLogin(userDTO.getLogin()).get();
        long countDeleted = personService.removeGreater(person, realUserDTO.getId());
        responseBody.addCommandResponseBody(String.format("Количество удаленных элементов, превышающих заданные : %d", countDeleted));
        return new Response(responseBody);
    }
}
