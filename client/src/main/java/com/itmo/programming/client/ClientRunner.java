package com.itmo.programming.client;


import com.itmo.programming.client.exceptions.InabilityConnectException;
import com.itmo.programming.commands.CommandResponse;
import com.itmo.programming.commands.TypeCommandResponse;
import com.itmo.programming.commands.exceptions.NoSuchCommandException;
import com.itmo.programming.communication.Request;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseCode;
import com.itmo.programming.console.ConsoleInterface;
import lombok.Data;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.util.Stack;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
public class ClientRunner {
    private static final String PS1 = "$ ";
    private static ClientRunner clientRunner;
    private Client client;
    private UserHandler userHandler;
    private ConsoleInterface consoleInterface;
    private Stack<Request> requests = new Stack<>();
    private final String  commandsForAuthorization = "Вы можете зарегистрироваться с помощью команды sign-up или войти с помощью команда sign-in";
    private ClientRunner() {
    }


    public static ClientRunner getClientRunner() {
        if (clientRunner == null) {
            clientRunner = new ClientRunner();
            return clientRunner;
        }
        return clientRunner;
    }

    public void init(UserHandler invoker, Client clientInstance) {
        userHandler = invoker;
        this.client = clientInstance;
    }

    public void start() throws IOException, InabilityConnectException {
        client.initSocketAddress();
        System.out.println("Клиент запущен");
        if (!client.connectToServer()) {
            client.close();
            throw new InabilityConnectException("Сервер недоступен");
        }
        //вынести в отдельный метод цикл
        while (true) {
            String line;
                consoleInterface.writeWithoutSpace(PS1);
                 line = consoleInterface.read();
            try {
                runCommand(line, consoleInterface);
            } catch (NoSuchCommandException e) {
                consoleInterface.write(e.getMessage());
            }
        }
    }

    public void runCommand(String line, ConsoleInterface consoleInterface) throws IOException {
        CommandResponse commandResponse = userHandler.execute(line, consoleInterface);
        if (commandResponse.getTypeCommandResponse() == TypeCommandResponse.ERROR) {
            consoleInterface.write(commandResponse.getData());
        } else {
            Request request = new Request(commandResponse.getUserDTO(), commandResponse.getCommandName(), commandResponse.getArgumentHolder());
            requests.add(request);
            try {
                consoleInterface.write(commandProcess());
            } catch (PortUnreachableException e) {
                if (!client.reconnectToServer()) {
                    consoleInterface.write("Не удалось подключиться к серверу");
                    System.exit(0);
                }
                consoleInterface.write("\nПереподключение прошло успешно");
                consoleInterface.write(commandProcess());
            }
        }
    }

    private String commandProcess() throws IOException {
        Request request = null;
        if (!requests.isEmpty()) {
            request = requests.peek();
            client.send(request);
        }
        Response response = client.receiveAnswer();
        Response response1 = null;
        if (response.getResponseCode() == ResponseCode.DATA_SIZE) {
            response1 = client.receiveAnswer(response.getBufferSize());
        }
        if (response1.getResponseCode() == ResponseCode.AUTHORIZED){
            userHandler.setAuthorized(true);
            client.setUserDTO(request.getUserDTO());
        }
        requests.pop();
        return response1.getResponseBody().getData();
    }

    public Client getClient() {
        return client;
    }
}
