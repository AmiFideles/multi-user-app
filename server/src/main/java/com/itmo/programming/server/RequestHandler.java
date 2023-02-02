package com.itmo.programming.server;

import com.itmo.programming.communication.Request;
import com.itmo.programming.communication.Response;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.controller.command.InvokerCommand;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class RequestHandler implements Runnable {
    public static ConcurrentLinkedQueue<Map.Entry<InetSocketAddress, Response>> responseQueue;
    private final ConsoleInterface consoleInterface = new ConsoleInterface(true);
    static {
        responseQueue = new ConcurrentLinkedQueue<>();
    }

    private final InvokerCommand invokerCommand;
    private final Map.Entry<InetSocketAddress, Request> requestEntry;

    public RequestHandler(InvokerCommand invokerCommand, Map.Entry<InetSocketAddress, Request> requestEntry) {
        this.requestEntry = requestEntry;
        this.invokerCommand = invokerCommand;
    }

    @Override
    public void run() {
        try {
            if (Objects.isNull(requestEntry.getKey())) {
                return;
            }
            consoleInterface.write("Получен запрос " + requestEntry.getValue().getCommandName() + " от клиента по адресу " + requestEntry.getKey().getAddress() + " и порту " +  requestEntry.getKey().getPort()); ;
            Response response = handle(requestEntry.getValue());
            responseQueue.add(new AbstractMap.SimpleEntry<>(requestEntry.getKey(), response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response handle(Request request) throws IOException {
        return invokerCommand.execute(request.getCommandName(), request.getArgumentHolder(), request.getUserDTO());
    }
}
