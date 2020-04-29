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
    private ClientSend clientSend;

    private List<Chatroom> chatroomList;
    private BlockingQueue<Packet> outgoingQueue;
    private BlockingQueue<Packet> incomingQueue;

    public Client() throws IOException {
        serverConnection = new Socket("localhost", 8000);

        chatroomList = new ArrayList<>();
        outgoingQueue = new ArrayBlockingQueue<>(100);
        incomingQueue = new ArrayBlockingQueue<>(100);

        clientReceive = new ClientReceive(serverConnection, incomingQueue);
        clientSend = new ClientSend(serverConnection, outgoingQueue);

        //clientPublish = new ClientPublish(incomingQueue);
        OutgoingSingleton.getInstance().setOutgoingQueue(outgoingQueue);
    }

    @Override
    public void run() {

        while(true){
            try {
                incomingQueue.take();
                System.out.println("Received message");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

