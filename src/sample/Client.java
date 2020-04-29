package sample;

import com.SimpleChat.Messages.Packet;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client implements Runnable{
    private Socket serverConnection;
    private String username;

    private ClientReceive clientReceive;
    private ClientPublish clientPublish;

    private List<Chatroom> chatroomList;
    private BlockingQueue<Packet> outgoingQueue;
    private BlockingQueue<Packet> incomingQueue;

    public Client() throws IOException {
        serverConnection = new Socket("localhost", 8000);

        chatroomList = new ArrayList<>();
        outgoingQueue = new ArrayBlockingQueue<>(100);
        incomingQueue = new ArrayBlockingQueue<>(100);

        clientReceive = new ClientReceive(serverConnection, incomingQueue);
        //clientPublish = new ClientPublish(incomingQueue);

    }

    @Override
    public void run() {

        while(true){
            try {
                incomingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}

