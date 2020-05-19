package sample;

import com.SimpleChat.Messages.Interfaces.Login;
import com.SimpleChat.Messages.Login.LoginFail;
import com.SimpleChat.Messages.Login.LoginSuccess;
import com.SimpleChat.Messages.Login.SignUpFail;
import com.SimpleChat.Messages.Login.SignUpSuccess;
import com.SimpleChat.Messages.Packet;
import javafx.application.Platform;
import sample.UI.Alert.AlertBox;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client extends Observable implements Runnable {
    private Socket serverConnection;
    private String username;
    private String clientID;

    private ClientReceive clientReceive;
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

        OutgoingSingleton.getInstance().setOutgoingQueue(outgoingQueue);
        thread = new Thread(this);
        thread.start();
    }

    public void setChangeStatus(){
        setChanged();
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    protected void handleMessage(Packet packet){
        if(packet.getMessage() instanceof Login){
            handleLoginMessage(packet);
        }
    }

    protected void handleLoginMessage(Packet packet) {
        Serializable message = packet.getMessage();
        if(message instanceof SignUpSuccess){
            System.out.println("Sign Up Success!");
            setChanged();
            notifyObservers(packet.getMessage());
        }
        else if(message instanceof SignUpFail){
//            SignUpFail signUpFail = (SignUpFail)message;
//            String fail = FailHandler.getCause(signUpFail);
//            AlertBox.display("Sign Up Fail", fail);
            System.out.println("SignUpFail");
            setChanged();
            notifyObservers(message);
        }
        else if(message instanceof LoginSuccess){
            System.out.println("login response");
            clientID = packet.getUserID();
            setChanged();
            notifyObservers(message);
        }
        else if(message instanceof LoginFail){
            System.out.println("Login fail");
            setChanged();
            notifyObservers(message);

        }

//        if(message instanceof SignUpResponse){
//            SignUpResponse response = (SignUpResponse)packet.getMessage();
//            if(response.isSuccess()){
//                AlertBox.display("Confirmation", "Sign Up Success!");
//                client.setChangeStatus();
//                client.notifyObservers(response);
//            }
//            else{
//                int failCause = response.getFailCause();
//                String s = FailHandler.handleFail(failCause);
//                AlertBox.display("Error", s);
//            }
//        }
    }

    @Override
    public void run() {
        System.out.println("run start for client");
        while(true){
            try {
                Packet packet = incomingQueue.take();
                handleMessage(packet);
//                if(packet.getMessage() instanceof Login){
//                    System.out.println("instance of login");
//                }
//                System.out.println("Received message type: " + packet.getMessageType());
//                messageHandler.handleMessage(packet);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

