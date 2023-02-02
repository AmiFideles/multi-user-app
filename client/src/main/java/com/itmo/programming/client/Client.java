package com.itmo.programming.client;

import com.itmo.programming.communication.Request;
import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseCode;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.dto.UserDTO;
import com.itmo.programming.serialization.Serialization;
import lombok.Data;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
public class Client {
    private final int bufferSize;
    private final int clientPort;
    private final String host;
    private final int port;
    private final int reconnectionTimeout;
    private DatagramChannel datagramChannel;
    private DatagramSocket datagramSocket;
    private InetSocketAddress clientSocketAddress;
    private InetSocketAddress serverSocketAddress;
    private ConsoleInterface consoleInterface;
    private UserDTO userDTO;
    public Client(String host, int port, int clientPort, int reconnectionTimeout, ConsoleInterface consoleInterface) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.clientPort = clientPort;
        this.bufferSize = 4096;
        this.consoleInterface = consoleInterface;
    }

    /*
     * соединение к серверу. Проверка валидного хоста и порта
     * */
    public void initSocketAddress() throws IOException {
        clientSocketAddress = new InetSocketAddress(clientPort);
        serverSocketAddress = new InetSocketAddress(InetAddress.getByName(host), port);
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(clientSocketAddress);
        datagramChannel.connect(serverSocketAddress);
        datagramSocket = datagramChannel.socket();
    }


    public void send(Request request) throws IOException {
        byte[] sendingDataBuffer = new byte[bufferSize];
        if (userDTO!=null){
            request.setUserDTO(userDTO);
        }
        sendingDataBuffer = Serialization.serializeObject(request);
        DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length);
        datagramSocket.send(sendingPacket);
    }


    public Response receiveAnswer() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        SocketAddress temp = datagramChannel.receive(buffer);
        return Serialization.deserializeObject(buffer.array());
    }

    public Response receiveAnswer(int bufferSize) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        SocketAddress temp = datagramChannel.receive(buffer);
        return Serialization.deserializeObject(buffer.array());
    }

    public boolean connectToServer() throws IOException {
        boolean success = false;
        try {
            send(new Request("connect"));
            Response responseWithDataSize = receiveAnswer();
            Response realResponse = null;
            if (responseWithDataSize.getResponseCode() == ResponseCode.DATA_SIZE){
                realResponse = receiveAnswer(responseWithDataSize.getBufferSize());
                if (realResponse==null){
                    return success;
                }
                if (realResponse.getResponseCode() == ResponseCode.CONNECTED) {
                    success = true;
                    consoleInterface.write(realResponse.getResponseBody().getData());
                }
            }
            return success;
        } catch (PortUnreachableException e) {
            return false;
        }
    }

    public boolean reconnectToServer() throws IOException {
        boolean succes = false;
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0l;
        int time = 0;
        while (elapsedTime < reconnectionTimeout) {
            try {
                time++;
                Thread.sleep(1000);
                consoleInterface.writeWithoutSpace("\rПотеряно соединение с сервером. Ожидание повторного подключение " + time + "/" + reconnectionTimeout/1000 +"ms");
                elapsedTime = new Date().getTime() - startTime;
                send(new Request("connect"));
                Response response = receiveAnswer();
                if (response.getResponseCode() == ResponseCode.CONNECTED) {
                    succes = true;
                    return succes;
                }
            } catch (PortUnreachableException e) {
                continue;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        consoleInterface.write("\n");
        return succes;
    }

    public void close() throws IOException {
        datagramSocket.close();
        datagramChannel.close();
    }

}



