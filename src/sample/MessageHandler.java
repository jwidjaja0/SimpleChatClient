package sample;

import com.SimpleChat.Messages.Login.Login;
import com.SimpleChat.Messages.Login.SignUpResponse;
import com.SimpleChat.Messages.Packet;
import sample.UI.Alert.AlertBox;

public class MessageHandler {

    public MessageHandler() {
    }

    public void handleMessage(Packet packet){
        if(packet.getMessage() instanceof Login){
            handleLoginMessage(packet);
        }
    }

    private void handleLoginMessage(Packet packet) {
        if(packet.getMessage() instanceof SignUpResponse){
            SignUpResponse response = (SignUpResponse)packet.getMessage();
            if(response.isSuccess()){
                AlertBox.display("Confirmation", "Sign Up Success!");
            }
            else{
                int failCause = response.getFailCause();
                String message = FailHandler.handleFail(failCause);
                AlertBox.display("Error", message);
            }
        }
    }

}
