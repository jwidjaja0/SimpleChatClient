package com.simplechat.UI.MenuLayout;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.Serializable;

public class MenuLayoutController {

    @FXML MenuItem loginRegisterButton;

    @FXML MenuItem personalRecordButton;

    @FXML MenuItem changeProfileInfo;

    @FXML MenuBar menuBar1;

    @FXML MenuItem logout;

    @FXML MenuItem deleteAccount;


//    public void initialize(){
//
//        logout.setDisable(true);
//        deleteAccount.setDisable(true);
//
//        loginRegisterButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
//            @Override
//            public void handle(javafx.event.ActionEvent event) {
//                loginRegister();
//            }
//        });
//
//        personalRecordButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                showStats();
//            }
//        });
//
//        changeProfileInfo.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                changeUserInfo();
//            }
//        });
//
//        logout.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                logoutAccount();
//            }
//        });
//
//        deleteAccount.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                deleteUserAccount();
//            }
//        });
//    }
//
//    @Override
//    public void messageProcessor(Serializable message){
//        if (message instanceof UserDeleteSuccess){
//            (new AlertFactory(message.toString())).displayAlert();
//            logoutAccount();//Log out
//        }
//        else if (message instanceof UserDeleteFail){
//            (new AlertFactory(message.toString())).displayAlert();
//        }
//        else if (message instanceof LogoutSuccess){
//            (new AlertFactory(message.toString())).displayAlert();
//            logout.setDisable(true);
//            deleteAccount.setDisable(true);
//        }
//        else if (message instanceof LogoutFail){
//            (new AlertFactory(message.toString())).displayAlert();
//        }
//        else{
//            controller.messageProcessor(message);//Send it first because alerts handled in subsequent screens
//            if (message instanceof LoginSuccess || message instanceof SignUpSuccess){
//                logout.setDisable(false);
//                deleteAccount.setDisable(false);
//            }
//        }
//
//    }
//
//    private void changeUserInfo(){
//        try{
//            controller = new GetUserInfoController();
//            FXMLLoader getUserInfo = new FXMLLoader(getClass().getResource("../SplashScreen/GetUserInfo/GetUserInfo.fxml"));
//            getUserInfo.setController(controller);
//            Parent getUserInfoWindow = getUserInfo.load();
//            Stage stage = new Stage();
//            stage.setTitle("Enter Your Information");
//            jMetro.setParent(getUserInfoWindow);
//
//            stage.setScene(new Scene(jMetro.getParent()));
//            ((GetUserInfoController) controller).setType("Change");
//            stage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void loginRegister(){
//        try{
//            FXMLLoader splashScreen = new FXMLLoader(getClass().getResource("../SplashScreen/SplashScreen.fxml"));
//            controller = new SplashController();
//            splashScreen.setController(controller);
//            Parent splashWindow = splashScreen.load();
//            Stage stage = new Stage();
//            stage.setTitle("Welcome!");
//
//            jMetro.setParent(splashWindow);
//            stage.setScene(new Scene(jMetro.getParent()));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void showStats(){
//        //Send request to server
//        MessageSender.getInstance().sendMessage("PlayerStatsRequest", new PlayerStatsRequest());
//    }
//
//    private void logoutAccount(){
//        MessageSender.getInstance().sendMessage("Login", new LogoutRequest());
//    }
//
//    private void deleteUserAccount(){
//        MessageSender.getInstance().sendMessage("UserUpdate", new UserDeleteRequest());
//    }

}
