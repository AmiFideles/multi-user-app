package com.itmo.programming.controller.command.modification;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.service.PersonService;
import com.itmo.programming.service.UserService;

import java.io.IOException;
import java.util.Optional;


public class ClearCommand extends Command {
    private final PersonService personService;
    private final UserService userService;
    public ClearCommand(PersonService personService, UserService userService) {
        super("clear", ": очистить коллекцию");
        this.personService = personService;
        this.userService = userService;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        Optional<UserDTO> byLogin = userService.getByLogin(userDTO.getLogin());
        long countDeleted = 0;
        if (byLogin.isPresent()){
            countDeleted = personService.clearByUserId(byLogin.get().getId());
        }
        if (countDeleted != 0) {
            responseBody.addCommandResponseBody(String.format("Количество удаленных элементов, созданных вами = %d", countDeleted));
        } else {
            responseBody.addCommandResponseBody("Вы можете удалять только те элементы, которые сами создали. На данный момент вы не создали ни одного элемента");
        }
        return new Response(responseBody);
    }
}
