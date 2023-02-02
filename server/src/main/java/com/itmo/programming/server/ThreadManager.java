package com.itmo.programming.server;

import java.util.concurrent.Executor;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class ThreadManager {
    private final Executor receiverExecutor;
    private final Executor requestHandlerExecutor;
    private final Executor senderExecutor;


    public ThreadManager(Executor receiverExecutor, Executor requestHandlerExecutor, Executor senderExecutor) {
        this.receiverExecutor = receiverExecutor;
        this.requestHandlerExecutor = requestHandlerExecutor;
        this.senderExecutor = senderExecutor;
    }

    public void receiveRequest(Runnable task) {
        receiverExecutor.execute(task);
    }

    public void handleRequest(Runnable task) {
        requestHandlerExecutor.execute(task);
    }

    public void sendResponse(Runnable task) {
        senderExecutor.execute(task);
    }
}
