package com.itmo.programming.controller.command.view;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.mapper.toentity.PersonDTOMapper;
import com.itmo.programming.model.Person;
import com.itmo.programming.service.PersonService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class PrintAscendingCommand extends Command {
    private final PersonService personService;

    public PrintAscendingCommand(PersonService personService) {
        super("print_ascending", ": выводит все элементы в порядке возрастания");
        this.personService = personService;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        List<Person> sortedPersonList = personService.findAll().stream().map(PersonDTOMapper.INSTANCE::toEntity).sorted().collect(Collectors.toList());
        if (sortedPersonList.size() == 0) {
            responseBody.addCommandResponseBody("В коллекции нету элементов. Вы можете добавить и потом вывести в порядке возрастания");
            return new Response(responseBody);
        }
        StringBuilder sb = new StringBuilder();
        sortedPersonList.forEach(sb::append);
        responseBody.addCommandResponseBody(sb.toString());
        return new Response(responseBody);
    }
}
