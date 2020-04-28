package sample;

import com.SimpleChat.Messages.Chat.ChatMessage;
import com.SimpleChat.Messages.Packet;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ClientPublish implements Runnable{

    private BlockingQueue<Packet> incomingQueue;

    public ClientPublish(BlockingQueue<Packet> incomingQueue) {
        this.incomingQueue = incomingQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                Packet packet = incomingQueue.take();
                if(packet.getMessage() instanceof ChatMessage){

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
