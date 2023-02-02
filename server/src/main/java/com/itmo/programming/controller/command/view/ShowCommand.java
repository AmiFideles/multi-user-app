package com.itmo.programming.controller.command.view;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.mapper.toentity.PersonDTOMapper;
import com.itmo.programming.model.Person;
import com.itmo.programming.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ShowCommand extends Command {
    private final PersonService personService;

    public ShowCommand(PersonService personService) {
        super("show", ": выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.personService = personService;

    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) {
        ResponseBody responseBody = new ResponseBody();
        List<String> tempList = new ArrayList<>();
        List<Person> all = personService.findAll().stream().map(PersonDTOMapper.INSTANCE::toEntity).collect(Collectors.toList());
        if (all.isEmpty()) {
            responseBody.addCommandResponseBody("Количество элементов: 0");
        }
        all.forEach(person -> tempList.add(person.toString()));
        responseBody.addCommandResponseBody(String.join("\n", tempList));
        return new Response(responseBody);
    }
}
