package com.simplechat.Client;

import com.SimpleChat.Messages.Chat.ChatMessage;
import com.SimpleChat.Messages.Chat.JoinChatroomSuccess;
import com.SimpleChat.Messages.Packet;
import com.SimpleChat.Messages.User.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.BlockingQueue;

public class Chatroom extends Observable {
    //Chatroom is being observed by its UI counterpart (RoomController with the same chatroom name)

    private String chatroomName;
    private ClientInfo clientInfo;
    private List<ChatMessage> chatMessageList;
    private List<UserInfo> userInfoList;

    public Chatroom(JoinChatroomSuccess joinChatroomSuccess, ClientInfo clientInfo) {
        chatroomName = joinChatroomSuccess.getChatroomDetail().getChatroomName();
        userInfoList = joinChatroomSuccess.getUserInfoList();
        chatMessageList = joinChatroomSuccess.getChatMessageHistory().getChatMessageList();

        this.clientInfo = clientInfo;
    }

    public void addChatMessage(ChatMessage chatMessage){
        chatMessageList.add(chatMessage);
        setChanged();
        notifyObservers(chatMessage);
    }

    public String getChatroomName() {
        return chatroomName;
    }

    public void updateUIMessageUsers(){
        setChanged();
        notifyObservers("Update");
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public List<ChatMessage> getChatMessageList() {
        return chatMessageList;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }
}
