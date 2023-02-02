package com.itmo.programming.server;

import com.itmo.programming.communication.Request;
import com.itmo.programming.serialization.Serialization;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ReceiverRequest implements Runnable {
    public static ConcurrentLinkedQueue<Map.Entry<InetSocketAddress, Request>> requestQueue;

    static {
        requestQueue = new ConcurrentLinkedQueue<>();
    }

    private final int bufferSize = 4096;
    private final DatagramChannel datagramChannel;
    private SocketAddress clientAddress;

    public ReceiverRequest(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }


    @Override
    public void run() {
        try {
            receiveRequest();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveRequest() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        clientAddress = datagramChannel.receive(byteBuffer);
        if (Objects.nonNull(clientAddress)) {
            Request request = Serialization.deserializeObject(byteBuffer.array());
            requestQueue.add(new AbstractMap.SimpleEntry<>((InetSocketAddress) clientAddress, request));
        }

    }

}
