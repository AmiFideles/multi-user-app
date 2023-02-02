package com.itmo.programming.controller.command;


import com.itmo.programming.controller.command.authorization.LoginCommand;
import com.itmo.programming.controller.command.authorization.RegisterCommand;
import com.itmo.programming.controller.command.exceptions.NoSuchCommandException;
import com.itmo.programming.controller.command.modification.*;
import com.itmo.programming.controller.command.connection.ConnectCommand;
import com.itmo.programming.controller.command.view.*;
import com.itmo.programming.service.PersonService;
import com.itmo.programming.service.UserService;
import com.itmo.programming.encryption.SHA512Generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Менеджер команд. Данный класс инициализирует команд и предоставляет возможность получить команду по ее названию
 */
public class CommandManager {
    private final UserService userService;
    private final PersonService personService;
    private final Map<String, Command> allCommand = new HashMap<>();

    /**
     * Конструктор, в котором инициализируются все команды
     */
    public CommandManager(UserService userService, PersonService personService) {
        this.userService = userService;
        this.personService = personService;
        initializeAllCommand();
    }

    /**
     * Метод позволяет получить объект Command по ее названию в виде string
     *
     * @param nameCommand название команды
     * @return объекта Command
     * @throws NoSuchCommandException выбрасывается, если не сущетсвует такой команды
     */
    public Command getCommand(String nameCommand) throws NoSuchCommandException {
        if (allCommand.containsKey(nameCommand)) {
            return allCommand.get(nameCommand);
        } else {
            throw new NoSuchCommandException(String.format("Команды %s не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды", nameCommand));
        }
    }

    /**
     * Инициализации всех команд и добавление мануаля для команды help
     */
    private void initializeAllCommand() {
        HelpCommand helpCommand = new HelpCommand();
        List<Command> commands = Stream.of(new ClearCommand(personService, userService), new CountLessThanLocation(personService), new HistoryCommand(), new InfoCommand(personService), new InsertCommand(personService, userService), new PrintAscendingCommand(personService), new RemoveAnyByBirthday(personService, userService), new RemoveCommand(personService, userService), new RemoveGreaterCommand(personService, userService), new RemoveGreaterKeyCommand(personService, userService), new ShowCommand(personService), new UpdateCommand(personService, userService), helpCommand).collect(Collectors.toList());
        convertToMap(commands);
        helpCommand.setHelpManual(prepareHelpManual());
        RegisterCommand registerCommand = new RegisterCommand(userService, new SHA512Generator());
        LoginCommand loginCommand = new LoginCommand(userService);
        ConnectCommand connectCommand = new ConnectCommand();
        allCommand.put(registerCommand.getName(), registerCommand);
        allCommand.put(loginCommand.getName(), loginCommand);
        allCommand.put(connectCommand.getName(), connectCommand);
    }

    /**
     * @return подготовка текста команды help
     */
    private HashMap<String, String> prepareHelpManual() {
        HashMap<String, String> helpText = new HashMap<>();
        helpText.put("execute_script", ": считает и исполняет скрипт из указанного файла. В скрипте должны содержатся команды в таком же виде, в котором вы вводите в интерактивном режиме");
        allCommand.forEach((key, value) -> helpText.put(value.getName(), value.getDescription()));
        return helpText;
    }


    /**
     * Конвертирует лист команды в map
     *
     * @param commands лист команд
     */
    private void convertToMap(List<Command> commands) {
        commands.forEach(x -> allCommand.put(x.getName(), x));
    }
}
