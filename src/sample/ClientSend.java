package sample;

import com.SimpleChat.Messages.Packet;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

public class ClientSend{

    private static ClientSend instance = new ClientSend();
    private String userID;
    private BlockingQueue<Packet> outgoingQueue;

    private ClientSend(){
    }

    public static ClientSend getInstance() {
        return instance;
    }

    public void sendMessage(String messageType, Serializable message){
        try {
            outgoingQueue.put(new Packet(messageType, userID, message));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void logOut(){
        userID = null;
    }

    public void setOutgoingQueue(BlockingQueue<Packet> outgoingQueue) {
        this.outgoingQueue = outgoingQueue;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
