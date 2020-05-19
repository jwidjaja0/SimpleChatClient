package sample.UI.Login;

import com.SimpleChat.Messages.Login.LoginFail;
import com.SimpleChat.Messages.Login.LoginRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.FailHandler;
import sample.OutgoingSingleton;
import sample.UI.Alert.AlertBox;


public class LoginController {
    @FXML
    TextField usernameText;
    @FXML TextField passwordText;

    @FXML
    Button exitButton;
    @FXML Button loginButton;

    public void initialize(){
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String username = usernameText.getText();
                String password = passwordText.getText();
                LoginRequest request = new LoginRequest(username, password);
                OutgoingSingleton.getInstance().sendMessage("LoginRequest", request);
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage)exitButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void close(){
        Platform.runLater(() -> ((Stage)exitButton.getScene().getWindow()).close());
    }

    public void loginSuccess(){
        Platform.runLater(() -> AlertBox.display("LoginSuccess", "Success!"));
        close();
    }
    public void loginFail(LoginFail loginFail){
        Platform.runLater(() -> {
            String message = FailHandler.getLoginFailCause(loginFail);
            AlertBox.display("LoginFail", message);
        });
    }
}
