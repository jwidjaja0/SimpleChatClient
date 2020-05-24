package com.simplechat.Client;

import com.SimpleChat.Messages.Chat.ChatMessage;
import com.SimpleChat.Messages.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientReceive implements Runnable {
    Socket connection;
    private BlockingQueue<Packet> incomingQueue;

    public ClientReceive(Socket connection, BlockingQueue<Packet> incomingQueue) {
        this.connection = connection;
        this.incomingQueue = incomingQueue;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            ObjectInputStream oiStream = new ObjectInputStream(connection.getInputStream());
            while(true){
                Packet packet = (Packet)oiStream.readObject();
                incomingQueue.put(packet);
                System.out.println("Added packet to incomingQueue type: " + packet.getClass().getName());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            System.out.println("ClientReceive closing");
        }
    }
}
