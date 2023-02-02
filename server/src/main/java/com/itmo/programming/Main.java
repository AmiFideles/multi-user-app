package com.itmo.programming;

import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.server.ServerRunner;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Главный класс программы
 *
 * @author Iskandarov Shakhzodbek P3133
 */
public class Main {
    private static int serverPort;

    public static void main(String[] args) {
        ConsoleInterface consoleManager = new ConsoleInterface(true);
        if (initServerPort()) {
            initTables();
            ServerRunner serverRunner = new ServerRunner(new InetSocketAddress(serverPort), consoleManager);
            serverRunner.start();
        }
    }

    private static void initTables() {
        try {
            Map<String, String> databaseProperties = getDatabaseProperties();
            Flyway flyway = Flyway.configure().dataSource(
                    databaseProperties.get("url"),
                    databaseProperties.get("username"),
                    databaseProperties.get("password")).
                    locations("classpath:/db/migration").
                    load();
            flyway.migrate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> getDatabaseProperties() throws IOException {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("flyway_conf.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            Map<String, String> map = new HashMap<>();
            map.put("url", properties.getProperty("flyway.url"));
            map.put("username", properties.getProperty("flyway.username"));
            map.put("password", properties.getProperty("flyway.password"));
            return map;
        }
    }

    private static boolean initServerPort() {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream("server.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            try {
                serverPort = Integer.parseInt(properties.getProperty("server.port"));
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Порт должен быть представлен числом");
            }
            return false;
        } catch (IOException ex) {
            System.out.println("Произошла ошибка при чтении порта");
            return false;
        }
    }
}
