package com.itmo.programming.client;

import com.itmo.programming.client.exceptions.InabilityConnectException;
import com.itmo.programming.client.exceptions.WrongAmountOfElementsException;
import com.itmo.programming.commands.CommandHolder;
import com.itmo.programming.console.ConsoleInterface;

import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class Application {
    private static final String CLIENT_CONFIGURATION_PATH = "client.properties";
    private static String host;
    private static int port;
    private static int clientPort;
    private static int reconnectionTime;

    public static void main(String[] args) throws IOException{
        if (!initializeConnectionData(args)) {
            System.out.println("Всего доброго!");
        } else {
            ClientRunner clientRunner = ClientRunner.getClientRunner();
            ConsoleInterface consoleInterface = new ConsoleInterface(true);
            Client client = new Client(host, port, clientPort, reconnectionTime, consoleInterface);
            clientRunner.setConsoleInterface(consoleInterface);
            clientRunner.init(new UserHandler(new CommandHolder()), client);
            try {
                clientRunner.start();
            } catch (InabilityConnectException e) {
                consoleInterface.write("Сервер недоступен");
            } catch (UnknownHostException e) {
                consoleInterface.write("Вы ввели неверное имя хоста");
            } catch (BindException e) {
                consoleInterface.write(String.format("Порт %d, по которому запускается клиентское приложение недоступен", client.getClientPort()));
            }

        }
    }

    public static boolean initializeConnectionData(String[] hostAndPortArgs) {
        try {
            if (hostAndPortArgs.length != 1) throw new WrongAmountOfElementsException();
            Map<String, String> clientProperties = getClientProperties();
            host = clientProperties.get("remote.server.address");
            port = Integer.parseInt(clientProperties.get("remote.server.port"));
            reconnectionTime = Integer.parseInt(clientProperties.get("client.reconnectionTime"));
            clientPort = Integer.parseInt(hostAndPortArgs[0]);
            if (port < 0) throw new ArithmeticException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            System.out.println("Использование: java -jar jarName  <ClientPort>");
            return false;
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            System.out.println("Порт должен быть представлен числом!");
            return false;
        } catch (ArithmeticException exception) {
            System.out.println("Порт не может быть отрицательным!");
            return false;
        } catch (IOException e) {
            System.out.println("Произошла ошибка при попытка чтении свойств");
            return false;
        }
    }

    private static Map<String, String> getClientProperties() throws IOException {
        try (InputStream is = Application.class.getClassLoader().getResourceAsStream(CLIENT_CONFIGURATION_PATH)) {
            Properties properties = new Properties();
            properties.load(is);
            Map<String, String> map = new HashMap<>();
            map.put("remote.server.address", properties.getProperty("remote.server.address"));
            map.put("remote.server.port", properties.getProperty("remote.server.port"));
            map.put("client.reconnectionTime", properties.getProperty("client.reconnectionTime"));
            return map;
        }
    }
}
