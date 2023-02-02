package com.itmo.programming.commands.withargument;

import com.itmo.programming.client.ClientRunner;
import com.itmo.programming.client.exceptions.InabilityConnectException;
import com.itmo.programming.commands.Command;
import com.itmo.programming.commands.CommandResponse;
import com.itmo.programming.commands.TypeCommandResponse;
import com.itmo.programming.commands.exceptions.EndlessLoopingException;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.console.ConsoleInterface;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ExecuteScriptCommand extends Command {
    private final static HashSet<String> path = new HashSet<>();
    private ConsoleInterface consoleInterface;

    public ExecuteScriptCommand() {
        super("execute_script", 1);
        this.consoleInterface = consoleInterface;
    }


    @Override
    public CommandResponse execute(ArgumentHolder argumentHolder, ConsoleInterface consoleInterface) {
        ArrayList<String> reply = new ArrayList<>();
        String verificationResult = checkCountOfArgument(argumentHolder.getCountParameter());
        if (verificationResult != null) {
            reply.add(verificationResult);
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        }
        String scriptPath = argumentHolder.getInputParameterLine()[0];
        long startTime = System.currentTimeMillis();
        path.add(scriptPath);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(scriptPath), StandardCharsets.UTF_8))) {
            ConsoleInterface fileInterface = new ConsoleInterface(bufferedReader, false);
            consoleInterface.write("Начинаем выполнять скрипт по адресу " + scriptPath);
            String inputLine;
            while ((inputLine = fileInterface.readLineFromFile()) != null) {
                String[] listScriptPath = inputLine.split(" ");
                if ("execute_script".equals(listScriptPath[0])) {
                    if (!path.contains(listScriptPath[1])) {
                        ClientRunner.getClientRunner().runCommand(inputLine, fileInterface);
                    } else {
                        throw new EndlessLoopingException("Вы пытаетесь закциклить выполнение. Хотите сломать программу???");
                    }
                } else {
                    ClientRunner.getClientRunner().runCommand(inputLine, fileInterface);
                }
            }
            reply.add(String.format("Скрипт %s выполнен успешно. Его выполнение заняло %d ms", scriptPath, System.currentTimeMillis() - startTime));
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        } catch (FileNotFoundException e) {
            if (!(new File(scriptPath).exists())){
                reply.add("Не существа скрипта по адресу " + scriptPath);
                return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
            }
            if (!(new File(scriptPath).canRead())){
                reply.add("Нету прав для чтения скрипта по адресу " + scriptPath);
                return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
            }
        } catch (EndlessLoopingException e) {
            reply.add(e.getMessage());
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        } catch (IOException e) {
            reply.add(e.getMessage());
            return new CommandResponse(argumentHolder, reply, TypeCommandResponse.ERROR);
        } finally {
            path.remove(scriptPath);
        }
        return null;
    }
}
