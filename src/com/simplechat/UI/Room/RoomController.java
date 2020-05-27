package com.simplechat.UI.Room;

import com.SimpleChat.Messages.Chat.ChatMessage;
import com.SimpleChat.Messages.Interfaces.User;
import com.SimpleChat.Messages.User.UserInfo;
import com.simplechat.Client.ClientInfo;
import com.simplechat.Client.OutgoingSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

public class RoomController {

    @FXML
    private Text roomNameText;

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField chatField;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<?> userList;

    private String roomName;
    private ClientInfo clientInfo;

    public void initialize(){
        chatArea.setEditable(false);
    }


    @FXML
    void sendChat(ActionEvent event) {
        String toSend = chatField.getText();
        UserInfo userInfo = new UserInfo(clientInfo.getNickname(), clientInfo.getClientID());

        ChatMessage message = new ChatMessage(roomName, toSend, userInfo);
        OutgoingSingleton.getInstance().sendMessage("Chat", message);
    }

    public void showChat(ChatMessage chatMessage){
        String message = chatMessage.getMessage();
        String prevText = chatArea.getText();

        StringBuilder builder = new StringBuilder(prevText);
        String name = chatMessage.getUserInfo().getNickname();
        builder.append("\n");
        builder.append(name);
        builder.append(": ");
        builder.append(chatMessage.getMessage());

        chatArea.setText(builder.toString()); //not Tested;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
}
