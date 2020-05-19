package sample;

import com.SimpleChat.Messages.Interfaces.Login;
import com.SimpleChat.Messages.Login.SignUpResponse;
import com.SimpleChat.Messages.Packet;
import sample.UI.Alert.AlertBox;

public class MessageHandler {

    private Client client;
    public MessageHandler(Client client) {
        this.client = client;
    }

    public void handleMessage(Packet packet){
//        if(packet.getMessageType().equals("Login")){
//            handleLoginMessage(packet);
//        }
        if(packet.getMessage() instanceof Login){
            handleLoginMessage(packet);
        }
    }

    private void handleLoginMessage(Packet packet) {
        if(packet.getMessage() instanceof SignUpResponse){
            SignUpResponse response = (SignUpResponse)packet.getMessage();
            if(response.isSuccess()){
                AlertBox.display("Confirmation", "Sign Up Success!");
                client.setChangeStatus();
                client.notifyObservers(response);
            }
            else{
                int failCause = response.getFailCause();
                String message = FailHandler.handleFail(failCause);
                AlertBox.display("Error", message);
            }
        }
    }

}
