package com.simplechat.Client;

import com.SimpleChat.Messages.Chat.JoinChatroomSuccess;
import com.SimpleChat.Messages.Interfaces.Chat;
import com.SimpleChat.Messages.Interfaces.Login;
import com.SimpleChat.Messages.Login.LoginFail;
import com.SimpleChat.Messages.Login.LoginSuccess;
import com.SimpleChat.Messages.Login.SignUpFail;
import com.SimpleChat.Messages.Login.SignUpSuccess;
import com.SimpleChat.Messages.Packet;

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
    private String clientID;

    private ClientInfo clientInfo;

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
        messageHandler = new MessageHandler(this);

        clientInfo = new ClientInfo();
        OutgoingSingleton.getInstance().setOutgoingQueue(outgoingQueue);
        thread = new Thread(this);
        thread.start();
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
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

    //Observer:LandingController
    protected void handleMessage(Packet packet){
        if(packet.getMessage() instanceof Login){
            handleLoginMessage(packet);
        }
        else if(packet.getMessage() instanceof Chat){
            handleChatMessage(packet);
        }
    }

    protected void handleChatMessage(Packet packet) {
        Serializable message = packet.getMessage();

        if(message instanceof JoinChatroomSuccess){
            JoinChatroomSuccess joinChatroomSuccess = (JoinChatroomSuccess)message;
            //Create new chatroom
            Chatroom chatroom = new Chatroom(joinChatroomSuccess.getChatroomDetail().getChatroomName());
            chatroomList.add(chatroom);

            //tell landing to make chatroom, make chatroomController oberver this chatroom
//            setChanged();
//            notifyObservers(message);

            //
        }

        setChanged();
        notifyObservers(message);
    }



    protected void handleLoginMessage(Packet packet) {
        Serializable message = packet.getMessage();
        if(message instanceof SignUpSuccess){
            System.out.println("Sign Up Success!");
        }
        else if(message instanceof SignUpFail){
            System.out.println("SignUpFail");
        }
        else if(message instanceof LoginSuccess){
            System.out.println("login response");
            clientID = packet.getUserID();
            clientInfo.setClientID(packet.getUserID());
        }
        else if(message instanceof LoginFail){
            System.out.println("Login fail");
        }
        setChanged();
        notifyObservers(message);

    }

    @Override
    public void run() {
        clientReceive = new ClientReceive(incomingQueue);
        clientSend = new ClientSend(outgoingQueue);
        clientSend.setSocket(serverConnection);
        clientReceive.setConnection(serverConnection);
        System.out.println("run start for client");

        while(true){
            try {
                Packet packet = incomingQueue.take();
                handleMessage(packet);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

