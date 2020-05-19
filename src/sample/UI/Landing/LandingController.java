package sample.UI.Landing;

import com.SimpleChat.Messages.Login.SignUpResponse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Client;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class LandingController implements Observer {
    @FXML
    Button loginButton;
    @FXML
    Button signUpButton;

    Stage stage;

    public void initialize(){
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent signupRoot = FXMLLoader.load(getClass().getResource("../Login/Signup.fxml"));
                    stage = new Stage();
                    stage.setTitle("SignUp");
                    stage.setScene(new Scene(signupRoot));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent loginRoot = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
                    stage = new Stage();
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
            if(arg instanceof SignUpResponse){
                SignUpResponse response = (SignUpResponse)arg;
                if(response.isSuccess()){
                    stage.close();
                }
            }
        }
    }
}
