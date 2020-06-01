package com.simplechat.Client;

import com.SimpleChat.Messages.Chat.JoinChatroomSuccess;
import com.SimpleChat.Messages.Interfaces.Chat;
import com.SimpleChat.Messages.Interfaces.Login;
import com.SimpleChat.Messages.Interfaces.User;
import com.SimpleChat.Messages.Login.*;
import com.SimpleChat.Messages.Packet;
import com.SimpleChat.Messages.User.UserInfo;

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
        OutgoingSingleton.getInstance().setClientInfo(clientInfo);

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
            Chatroom chatroom = new Chatroom(joinChatroomSuccess, clientInfo);
            chatroomList.add(chatroom);


        }

        //bad, need to let this class handle sending the JoinRoomRequest instead of landingController
        setChanged();
        notifyObservers(message);


    }

    public Chatroom findChatroomByName(String roomName){
        for(Chatroom ch: chatroomList){
            if(ch.getChatroomName().equals(roomName)){
                return ch;
            }
        }
        return null;
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

            LoginSuccess loginSuccess = (LoginSuccess)packet.getMessage();
            UserInfo userInfo = loginSuccess.getUserInfo();
            clientInfo.setNickname(userInfo.getNickname());
            clientInfo.setClientID(userInfo.getClientID());

        }
        else if(message instanceof LoginFail){
            System.out.println("Login fail");
        }
        else if(message instanceof LogOutSuccess){
            clientInfo.setClientID("");
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

//        OutgoingSingleton.getInstance().setUserID(clientID);
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

