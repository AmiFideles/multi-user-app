package com.itmo.programming.controller.command.modification;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseBody;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.service.PersonService;
import com.itmo.programming.service.UserService;

import java.io.IOException;
import java.time.LocalDate;


public class RemoveAnyByBirthday extends Command {
    private final PersonService personService;
    private final UserService userService;

    public RemoveAnyByBirthday(PersonService personService, UserService userService) {
        super("remove_any_by_birthday", ": удаляет из коллекции один элемент, значение поля birthday которого эквивалентно заданному");
        this.personService = personService;
        this.userService = userService;
    }

    @Override
    public Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException {
        ResponseBody responseBody = new ResponseBody();
        UserDTO realUserDTO = userService.getByLogin(userDTO.getLogin()).get();
        LocalDate localDate = argumentHolder.getBirthday();
        if (personService.removeByBirthdayAndUserId(localDate, realUserDTO.getId()) != 0) {
            responseBody.addCommandResponseBody("Один из элементов с такой датой рождения был успешно удален. Его id равен %d");
        } else {
            responseBody.addCommandResponseBody("Вы не можете удалить элемент с такой датой рождения");
        }
        return new Response(responseBody);
    }
}
