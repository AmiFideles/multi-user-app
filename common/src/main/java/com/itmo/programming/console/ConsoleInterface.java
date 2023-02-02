package com.itmo.programming.console;

/**
 * @author Iskandarov Shakhzodbek P3133
 */

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Класс отвечает за обмен информации в консоли с пользователям
 */
public class ConsoleInterface {
    private final String lineSeparator = "\n";
    private Writer writer;
    private boolean consoleMode;
    private BufferedReader reader;
    private Scanner scanner;

    /**
     * Констуктор, который создает consoleInterface и определяет куда писать данные и откуда считывать
     *
     * @param consoleMode флаг, отвечающий за режим работы
     */

    public ConsoleInterface(boolean consoleMode) {
        this.scanner = new Scanner(System.in);
        this.writer = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
        this.consoleMode = consoleMode;
    }
    public ConsoleInterface(BufferedReader reader, boolean interactive) {
        this.writer = new OutputStreamWriter(System.out, StandardCharsets.UTF_8);
        this.reader = reader;
        this.consoleMode = interactive;
    }

    /**
     * Метод для считывания.
     *
     * @return возращает считаную строку.
     */
    public String read() throws IOException {
        return consoleMode ? scanner.nextLine() : reader.readLine();

    }

    /**
     * Выводит сообщение для пользователя
     *
     * @param message строкад для вывода
     */
    public void write(String message) {
        try {
            writer.write(message);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeWithoutSpace(String message) {
        try {
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, заправшивает ввод данных. Перед вводом выводится пользователю сообщение
     *
     * @param message  сообщение для пользователя, будет выведено перед вводом
     * @param nullable если true, то значение может быть null, иначе false
     * @return введенное пользователем строка
     */
    public String readWithMessage(String message, boolean nullable) {
        String text = !nullable ? "Данное значение не может быть пустым" : "";
        String line = "";
        do {
            if (consoleMode) {
                write(message + text);
            }
            try {
                line = read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null || line.isEmpty() || line.trim().isEmpty()) {
                line = null;
            }
            //line = line.isEmpty() ? null : line;
        } while (!nullable && line == null);
        return line;
    }

    public String readLineFromFile() throws IOException {
        return reader.readLine();
    }

    /**
     * @return возвращает режим работы ConsoleInterface
     */
    public boolean isConsoleMode() {
        return consoleMode;
    }

    public void setFileMode(BufferedReader bufferedReader) {
        this.reader = reader;
        this.consoleMode = false;
    }

    public void setConsoleMode() {
        this.consoleMode = true;
    }

}
