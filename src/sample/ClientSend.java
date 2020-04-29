package sample;

import com.SimpleChat.Messages.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ClientSend implements Runnable{
    private Socket socket;
    private BlockingQueue<Packet> outgoingQueue;

    public ClientSend(Socket socket, BlockingQueue<Packet> outgoingQueue) {
        this.socket = socket;
        this.outgoingQueue = outgoingQueue;
    }

    @Override
    public void run() {

        try {
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            while(true){
                System.out.println("Sending message to server");
                toServer.writeObject(outgoingQueue.take());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("ClientSend closing");
        }
    }
}
