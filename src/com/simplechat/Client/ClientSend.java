package com.simplechat.Client;

import com.SimpleChat.Messages.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientSend implements Runnable{
    private Socket socket;
    private BlockingQueue<Packet> outgoingQueue;

    public ClientSend(BlockingQueue<Packet> outgoingQueue) {
        this.outgoingQueue = outgoingQueue;
        System.out.println("clientSend started");

        Thread self = new Thread(this);
        self.start();
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            //If no connection, throwsNullPointerException, TODO: add handling of NullPointerException
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            while(true){
                System.out.println("Sending message to server");
                toServer.writeObject(outgoingQueue.take());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("ClientSend closing");
        }
    }
}
