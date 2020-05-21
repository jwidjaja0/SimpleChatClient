package sample.UI.Landing;

import com.SimpleChat.Messages.Interfaces.Login;
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
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Client;
import sample.FailHandler;
import sample.UI.Alert.AlertBox;
import sample.UI.Login.LoginController;
import sample.UI.Login.SignupController;
import sample.UI.SplashScreen.SplashScreenController;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class LandingController implements Observer {

    @FXML
    MenuItem loginRegisterItem;
    @FXML MenuItem signOutItem;
    @FXML MenuItem personalItem;
    @FXML MenuItem aboutItem;

    private Stage thisStage;
    private LoginController loginController;
    private SignupController signupController;

    private SplashScreenController splashScreenController;

    public void initialize(){
        loginRegisterItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loginRegister();
            }
        });

    }

    private void loginRegister(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../SplashScreen/SplashScreen.fxml"));
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


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Client){
            if(arg instanceof Login){
                splashScreenController.messageProcessor(arg);
            }
//            else if(arg instanceof SignUpFail){
//                signupController.signupFail((SignUpFail)arg);
//            }
//            else if(arg instanceof LoginSuccess){
//                loginController.loginSuccess();
//            }
//            else if(arg instanceof LoginFail){
//                loginController.loginFail((LoginFail)arg);
//            }

        }
    }
}
