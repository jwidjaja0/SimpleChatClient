package sample.UI.Landing;

import com.SimpleChat.Messages.Login.LoginFail;
import com.SimpleChat.Messages.Login.LoginSuccess;
import com.SimpleChat.Messages.Login.SignUpFail;
import com.SimpleChat.Messages.Login.SignUpSuccess;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Client;
import sample.FailHandler;
import sample.UI.Alert.AlertBox;
import sample.UI.Login.LoginController;
import sample.UI.Login.SignupController;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class LandingController implements Observer {
    @FXML
    Button loginButton;
    @FXML
    Button signUpButton;

    private Stage stage;
    private LoginController loginController;
    private SignupController signupController;

    public void initialize(){


        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Login/Signup.fxml"));
                    Parent signupRoot = loader.load();
                    signupController = loader.getController();

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("SignUp");
                    stage.setScene(new Scene(signupRoot));
                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Login/Login.fxml"));
                    Parent loginRoot = loader.load();
                    loginController = loader.getController();
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setTitle("Login");
                    stage.setScene(new Scene(loginRoot));
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
            if(arg instanceof SignUpSuccess){
                signupController.signupSuccess();
            }
            else if(arg instanceof SignUpFail){
                signupController.signupFail((SignUpFail)arg);
            }
            else if(arg instanceof LoginSuccess){
                loginController.loginSuccess();
            }
            else if(arg instanceof LoginFail){
                loginController.loginFail((LoginFail)arg);
            }

        }
    }
}
