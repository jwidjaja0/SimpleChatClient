package sample.UI.Login;

import com.SimpleChat.Messages.Login.LoginRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.OutgoingSingleton;


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
}
