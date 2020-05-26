package com.simplechat.UI.Room;

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
    private TextArea textBox;

    @FXML
    private TextField chatField;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<?> userList;

    public void initialize(){
        textBox.setEditable(false);
    }

    @FXML
    void sendChat(ActionEvent event) {

    }
}
