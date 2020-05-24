package com.simplechat.Client;

import com.SimpleChat.Messages.Chat.ChatMessage;
import com.SimpleChat.Messages.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Chatroom {
    private String chatroomName;
    private List<ChatMessage> chatMessageList;
    private BlockingQueue<Packet> outgoingMessage;

    public Chatroom(String chatroomName, BlockingQueue<Packet> outgoingMessage) {
        this.chatroomName = chatroomName;
        this.outgoingMessage = outgoingMessage;
        chatMessageList = new ArrayList<>();

    }


}
