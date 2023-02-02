 package com.itmo.programming.controller.command;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.communication.Response;
import com.itmo.programming.dto.UserDTO;

import java.io.IOException;

/**
 * Абстрактный класс для команд
 */
public abstract class Command {
    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract Response execute(ArgumentHolder argumentHolder, UserDTO userDTO) throws IOException;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
