package com.itmo.programming.controller.command.view;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.mapper.toentity.LocationDTOMapper;
import com.itmo.programming.service.PersonService;

import java.io.IOException;


public class CountLessThanLocation extends Command {
    private final PersonService personService;

    public CountLessThanLocation(PersonService personService) {
        super("count_less_than_location", ": выводит количество элементов, значение поля location которых меньше заданного");
        this.personService = personService;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        long count = personService.countLessThanLocation(LocationDTOMapper.INSTANCE.toEntity(argumentHolder.getInputLocation()));
        responseBody.addCommandResponseBody("количество элементов, значение поля location которых меньше заданного : " + count);
        return new Response(responseBody);
    }
}
