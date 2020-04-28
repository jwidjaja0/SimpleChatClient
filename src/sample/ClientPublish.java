package sample;

import com.SimpleChat.Messages.Packet;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ClientPublish implements Runnable{
    private List<Chatroom> chatroomList;
    private BlockingQueue<Packet> incomingQueue;

    public ClientPublish(List<Chatroom> chatroomList, BlockingQueue<Packet> incomingQueue) {
        this.chatroomList = chatroomList;
        this.incomingQueue = incomingQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                Packet packet = incomingQueue.take();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
