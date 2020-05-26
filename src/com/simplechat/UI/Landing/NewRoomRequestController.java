package com.simplechat.UI.Landing;

import com.SimpleChat.Messages.Chat.NewChatroomRequest;
import com.simplechat.Client.OutgoingSingleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NewRoomRequestController {
    @FXML
    TextField roomNameField;
    @FXML
    PasswordField passwordField;


    private Stage stage;


    public void sendRequest(ActionEvent actionEvent) {
        String roomName = roomNameField.getText();
        String password = passwordField.getText();
        NewChatroomRequest request = new NewChatroomRequest(roomName, password);
        OutgoingSingleton.getInstance().sendMessage("Chat", request);
    }

    public void closeWindow(){
        Platform.runLater(() -> ((Stage)roomNameField.getScene().getWindow()).close());
    }


}
