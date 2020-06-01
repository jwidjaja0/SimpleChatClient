package com.simplechat.Client;

import com.SimpleChat.Messages.Login.LogOutRequest;
import com.SimpleChat.Messages.Packet;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

public class OutgoingSingleton {

    private static OutgoingSingleton instance = new OutgoingSingleton();
    private ClientInfo clientInfo;
    private BlockingQueue<Packet> outgoingQueue;

    private OutgoingSingleton(){
    }

    public static OutgoingSingleton getInstance() {
        return instance;
    }

    public void sendMessage(String messageType, Serializable message){
        try {

            System.out.println("singleton putting to queue");
            outgoingQueue.put(new Packet(messageType, clientInfo.getClientID(), message));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setOutgoingQueue(BlockingQueue<Packet> outgoingQueue) {
        this.outgoingQueue = outgoingQueue;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }
}
