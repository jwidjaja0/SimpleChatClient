package sample;

import com.SimpleChat.Messages.Interfaces.Login;
import com.SimpleChat.Messages.Packet;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client extends Observable implements Runnable {
    private Socket serverConnection;
    private String username;

    private ClientReceive clientReceive;
    private ClientPublish clientPublish;
    private ClientSend clientSend;
    private MessageHandler messageHandler;

    private List<Chatroom> chatroomList;
    private BlockingQueue<Packet> outgoingQueue;
    private BlockingQueue<Packet> incomingQueue;

    private Thread thread;

    public Client() throws IOException {
        serverConnection = new Socket("localhost", 8000);

        chatroomList = new ArrayList<>();
        outgoingQueue = new ArrayBlockingQueue<>(100);
        incomingQueue = new ArrayBlockingQueue<>(100);

        clientReceive = new ClientReceive(serverConnection, incomingQueue);
        clientSend = new ClientSend(serverConnection, outgoingQueue);
        messageHandler = new MessageHandler(this);

        //clientPublish = new ClientPublish(incomingQueue);
        OutgoingSingleton.getInstance().setOutgoingQueue(outgoingQueue);
        thread = new Thread(this);
        thread.start();
    }

    public void setChangeStatus(){
        setChanged();
    }


    @Override
    public void run() {
        System.out.println("run start for client");
        while(true){
            try {
                Packet packet = incomingQueue.take();
                if(packet.getMessage() instanceof Login){
                    System.out.println("instance of login");
                }
                System.out.println("Received message type: " + packet.getMessageType());
                messageHandler.handleMessage(packet);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

