package com.itmo.programming.server;

import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.controller.command.CommandManager;
import com.itmo.programming.controller.command.InvokerCommand;
import com.itmo.programming.server.exceptions.InabilityOpenServer;
import com.itmo.programming.utils.HibernateUtils;
import com.itmo.programming.utils.ServiceManager;

import java.io.Console;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ServerRunner {
    private static final String PS = "$";
    Selector selector;
    private InvokerCommand invokerCommand;
    private CommandManager commandManager;
    private ReceiverRequest receiverRequest;
    private DatagramChannel datagramChannel;
    private InetSocketAddress serverSocketAddress;
    private ConsoleInterface consoleInterface;
    private Console console;
    private int serverPort;
    private ThreadManager threadManager;


    public ServerRunner(InetSocketAddress serverSocketAddress, ConsoleInterface consoleInterface) {
        this.serverSocketAddress = serverSocketAddress;
        this.consoleInterface = consoleInterface;
    }

    public void init() throws InabilityOpenServer, IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(serverSocketAddress);
        selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        receiverRequest = new ReceiverRequest(datagramChannel);
        console = System.console();
        ServiceManager serviceManager = new ServiceManager();
        commandManager = new CommandManager(serviceManager.getUserService(), serviceManager.getPersonService());
        invokerCommand = new InvokerCommand(commandManager);
        threadManager = new ThreadManager(Executors.newFixedThreadPool(10), new ForkJoinPool(Runtime.getRuntime().availableProcessors()), Executors.newCachedThreadPool());

    }

    public void start() {
        try {
            processInterruptHandler();
            init();
            consoleInterface.write("Сервер запущен");
            consoleInterface.write("Доступные команды для сервера: exit");
            while (true) {
                checkCommands();
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isReadable()) {

                        threadManager.receiveRequest(new ReceiverRequest(datagramChannel));
                        continue;
                    }
                    if (key.isWritable() && RequestHandler.responseQueue.size() > 0) {
                        threadManager.sendResponse(new SenderResponse(datagramChannel, RequestHandler.responseQueue.poll()));
                    }
                }
                if (ReceiverRequest.requestQueue.size() > 0) {
                    threadManager.handleRequest(new RequestHandler(invokerCommand, ReceiverRequest.requestQueue.poll()));
                }


            }
        } catch (BindException e) {
            consoleInterface.write(String.format("Порт %d, по которому сервер запускается недоступен", serverSocketAddress.getPort()));
        } catch (InabilityOpenServer | InstantiationException | IOException | InvocationTargetException | NoSuchMethodException | IllegalAccessException inabilityOpenServer) {
            inabilityOpenServer.printStackTrace();
        }
    }


    //TODO поменять на поток
    private void checkCommands() throws IOException {
        if (System.in.available() > 0) {
            String line = console.readLine();
            if ("exit".equals(line)) {
                close();
            } else {
                consoleInterface.write("Такой команды не существует");
            }
        }
    }

    private void processInterruptHandler() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                HibernateUtils.closeSessionFactory();
                close();
            }
        });

    }

    public void close() {
        System.out.println(" Сервер заканчивает работу");
        try {
            datagramChannel.close();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
