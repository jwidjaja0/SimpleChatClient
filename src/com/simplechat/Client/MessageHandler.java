package com.simplechat.Client;

import com.SimpleChat.Messages.Interfaces.Login;
import com.SimpleChat.Messages.Login.LoginSuccess;
import com.SimpleChat.Messages.Login.SignUpSuccess;
import com.SimpleChat.Messages.Packet;
import com.simplechat.Client.Client;
import com.simplechat.UI.Alert.AlertBox;

import java.io.Serializable;

public class MessageHandler {

    private Client client;
    public MessageHandler(Client client) {
        this.client = client;
    }

    public void handleMessage(Packet packet){
        if(packet.getMessage() instanceof Login){
            handleLoginMessage(packet);
        }

    }

    private void handleLoginMessage(Packet packet) {
        Serializable message = packet.getMessage();
        if(message instanceof SignUpSuccess){
            System.out.println("Sign Up Success!");
            AlertBox.display("Sign Up Successful", "Sign Up Successful!");
            client.setChangeStatus();
            client.notifyObservers();
        }
        else if(message instanceof LoginSuccess){
            System.out.println("login response");
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

}
