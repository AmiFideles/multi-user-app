package com.itmo.programming.controller.command.view;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.service.PersonService;

import java.io.IOException;


public class InfoCommand extends Command {
    private final PersonService personService;

    public InfoCommand(PersonService personService) {
        super("info", ": выводит в стандартный поток вывода информацию о коллекции (тип, количество элементов и т.д.)");
        this.personService = personService;
    }
    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        responseBody.addCommandResponseBody("Элементы хранятся по данному шаблону <Ключ Значение>");
        responseBody.addCommandResponseBody(String.format("Количество элементов: %d", personService.findAll().size()));
        return new Response(responseBody);
    }
}
