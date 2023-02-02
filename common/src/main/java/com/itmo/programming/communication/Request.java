package com.itmo.programming.communication;



import com.itmo.programming.dto.UserDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
public class Request implements Serializable {
    private static final long serialVersionUID = -5570790526731334044L;
    private String commandName;
    private ArgumentHolder argumentHolder;
    private UserDTO userDTO;


    public Request(UserDTO userDTO, String commandName, ArgumentHolder argumentHolder) {
        this.userDTO = userDTO;
        this.commandName = commandName;
        this.argumentHolder = argumentHolder;
    }

    public Request(String commandName) {
        this.commandName = commandName;
    }
}
