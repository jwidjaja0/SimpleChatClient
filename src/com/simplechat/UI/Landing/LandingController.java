package com.simplechat.UI.Landing;

import com.SimpleChat.Messages.Chat.ChatMessage;
import com.SimpleChat.Messages.Chat.JoinChatroomRequest;
import com.SimpleChat.Messages.Chat.JoinChatroomSuccess;
import com.SimpleChat.Messages.Chat.NewChatroomSuccess;
import com.SimpleChat.Messages.Interfaces.Login;
import com.SimpleChat.Messages.Interfaces.User;
import com.SimpleChat.Messages.Login.LogOutRequest;
import com.SimpleChat.Messages.Login.LoginSuccess;
import com.SimpleChat.Messages.User.UserInfo;
import com.simplechat.Client.Chatroom;
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


    public void initialize(){


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

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
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

    private void createNewRoom(ClientInfo clientInfo, JoinChatroomSuccess joinChatroomSuccess, Client client){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String chatroomName = joinChatroomSuccess.getChatroomDetail().getChatroomName();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/simpleChat/UI/Room/Room.fxml"));
                try {
                    Parent parent = loader.load();
                    Stage stage = new Stage();
                    stage.setTitle(chatroomName);
                    stage.setScene(new Scene(parent));

                    RoomController roomController = loader.getController();
                    roomController.setRoomName(chatroomName);
                    roomController.setClientInfo(clientInfo);

                    Chatroom ch = client.findChatroomByName(chatroomName);
                    ch.addObserver(roomController);
                    ch.updateUIMessageUsers();

//                    roomControllerList.add(roomController);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Client){
            if(arg instanceof LoginSuccess){
                LoginSuccess loginSuccess = (LoginSuccess)arg;
                UserInfo userInfo = loginSuccess.getUserInfo();

                //TODO:Remove clientID reference, use ClientInfo
                clientID = userInfo.getClientID();

                clientInfo.setClientID(userInfo.getClientID());
                clientInfo.setNickname(userInfo.getNickname());

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
                UserInfo info = new UserInfo(clientInfo.getNickname(), clientInfo.getClientID());
                OutgoingSingleton.getInstance().sendMessage("Chat", new JoinChatroomRequest(success.getRoomName(), success.getPassword(), info));

//                //now create the room
//                createNewRoom(success.getName(), clientInfo);
            }

            if(arg instanceof JoinChatroomSuccess){
                //create new room and put myself there
                JoinChatroomSuccess success = (JoinChatroomSuccess)arg;
                createNewRoom(clientInfo, success, (Client)o);

            }

        }
    }
}
