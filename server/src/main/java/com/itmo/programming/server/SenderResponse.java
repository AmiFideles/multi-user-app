package com.itmo.programming.server;

import com.itmo.programming.communication.Response;
import com.itmo.programming.communication.ResponseCode;
import com.itmo.programming.serialization.Serialization;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Map;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class SenderResponse implements Runnable {
    private final DatagramChannel datagramChannel;
    private final Map.Entry<InetSocketAddress, Response> responseEntry;

    public SenderResponse(DatagramChannel datagramChannel, Map.Entry<InetSocketAddress, Response> responseEntry) {
        this.datagramChannel = datagramChannel;
        this.responseEntry = responseEntry;
    }

    @Override
    public void run() {
        try {
            sendResponse(responseEntry.getValue(), responseEntry.getKey());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendResponse(Response response, InetSocketAddress clientSocketAddress) throws IOException {
        byte[] responseBytes = Serialization.serializeObject(response);
        Response dataSizeResponse = new Response(responseBytes.length, ResponseCode.DATA_SIZE);
        byte[] data = Serialization.serializeObject(dataSizeResponse);
        ByteBuffer dataBuffer = ByteBuffer.wrap(data);
        datagramChannel.send(dataBuffer, clientSocketAddress);
        ByteBuffer responseBuffer = ByteBuffer.wrap(responseBytes);
        datagramChannel.send(responseBuffer, clientSocketAddress);
    }
}
