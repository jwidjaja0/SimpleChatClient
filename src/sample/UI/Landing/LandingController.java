package sample.UI.Landing;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LandingController {
    @FXML
    Button loginButton;
    @FXML
    Button signUpButton;

    public void initialize(){
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent signupRoot = FXMLLoader.load(getClass().getResource("../Login/Signup.fxml"));
                    Stage stage = new Stage();
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
                    Stage stage = new Stage();
                    stage.setTitle("Login");
                    stage.setScene(new Scene(loginRoot));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
