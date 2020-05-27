package com.simplechat.UI.Landing;

import com.SimpleChat.Messages.Chat.ChatMessage;
import com.SimpleChat.Messages.Chat.JoinChatroomRequest;
import com.SimpleChat.Messages.Chat.NewChatroomSuccess;
import com.SimpleChat.Messages.Interfaces.Login;
import com.SimpleChat.Messages.Login.LogOutRequest;
import com.SimpleChat.Messages.Login.LoginSuccess;
import com.SimpleChat.Messages.User.UserInfo;
import com.simplechat.Client.ClientInfo;
import com.simplechat.Client.OutgoingSingleton;
import com.simplechat.UI.Login.LoginController;
import com.simplechat.UI.Login.SignupController;
import com.simplechat.UI.Room.RoomController;
import com.simplechat.UI.SplashScreen.SplashScreenController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import com.simplechat.Client.Client;
import com.simplechat.UI.Alert.ConfirmBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class LandingController implements Observer {

    @FXML
    MenuItem loginRegisterItem;
    @FXML MenuItem signOutItem;
    @FXML MenuItem personalItem;
    @FXML MenuItem aboutItem;

    @FXML AnchorPane myPane;
    @FXML Button newRoomButton;
    @FXML Button joinButton;

    private String clientID;
    private ClientInfo clientInfo;

    private Stage pStage;

    private LoginController loginController;
    private SignupController signupController;
    private SplashScreenController splashScreenController;
    private NewRoomRequestController newRoomRequestController;

    private List<RoomController> roomControllerList;

    public void initialize(){

        roomControllerList = new ArrayList<>();

        loginRegisterItem.setOnAction(actionEvent -> loginRegister());
        signOutItem.setDisable(true);
        signOutItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                OutgoingSingleton.getInstance().sendMessage("Login", new LogOutRequest());
                loginRegisterItem.setDisable(false);
            }
        });

        newRoomButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newRoomRequest();
            }
        });

    }

    public void setClose(){
        pStage = (Stage)myPane.getScene().getWindow();
        pStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                windowEvent.consume();
                closeProgram();
            }
        });

    }

    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Exit", "Confirm close program?");
        if(answer){
            if(clientID != null){
                OutgoingSingleton.getInstance().sendMessage("Login", new LogOutRequest());
            }
            System.exit(0);
        }
    }

    public void setClientID(String clientID){
        this.clientID = clientID;
    }

    private void loginRegister(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/simpleChat/UI/SplashScreen/SplashScreen.fxml"));
            Parent window = loader.load();
            splashScreenController = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Welcome!");
            stage.setScene(new Scene(window));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void newRoomRequest(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/simpleChat/UI/Landing/NewRoomRequest.fxml"));
        try {
            Parent window = loader.load();
            newRoomRequestController = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("New Chatroom");
            stage.setScene(new Scene(window));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNewRoom(String chatroomName, ClientInfo clientInfo){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/simpleChat/UI/Room/Room.fxml"));
                try {
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle(chatroomName);
                    stage.setScene(new Scene(parent));

                    RoomController roomController = loader.getController();
                    roomController.setRoomName(chatroomName);
                    roomController.setClientInfo(clientInfo);
                    roomControllerList.add(roomController);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void addMessage(ChatMessage chatMessage){
        for(RoomController rc : roomControllerList){
            if(rc.getRoomName().equals(chatMessage.getChatroomName())){

            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Client){
            if(arg instanceof LoginSuccess){
                clientID = ((LoginSuccess)arg).getClientID();
                signOutItem.setDisable(false);
                loginRegisterItem.setDisable(true);

            }
            if(arg instanceof Login){
                splashScreenController.messageProcessor(arg);
            }

            if(arg instanceof NewChatroomSuccess){
                newRoomRequestController.closeWindow(); //close request window

                //success creating new chatroom, send request to join automatically, open new room for client
                NewChatroomSuccess success = (NewChatroomSuccess)arg;
                OutgoingSingleton.getInstance().sendMessage("Chat", new JoinChatroomRequest(success.getName(), success.getPassword()));

                //now create the room
                createNewRoom(success.getName(), clientInfo);

            }

        }
    }
}
