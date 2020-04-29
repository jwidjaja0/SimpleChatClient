package sample.UI.Login;

import com.SimpleChat.Messages.Login.SignUpRequest;
import com.SimpleChat.Messages.Packet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ClientSend;

import javax.swing.*;


public class SignupController {
    @FXML
    TextField usernameText;
    @FXML
    PasswordField passwordText;
    @FXML
    PasswordField confirmText;
    @FXML
    TextField firstNameText;
    @FXML
    TextField lastNameText;
    @FXML
    TextField emailText;

    @FXML
    Button exitButton;
    @FXML Button signupButton;

    private SignUpRequest signUpRequest;

    public void initialize(){
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage)exitButton.getScene().getWindow();
                stage.close();
            }
        });

        signupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String username = usernameText.getText().trim();
                String password = passwordText.getText();
                String confirm = confirmText.getText();
                if(!password.equals(confirm)){
                    //send alert that passwords are not equal
                    JOptionPane.showMessageDialog(null, "Password not equal");
                }
                String firstName = firstNameText.getText().trim();
                String lastName = lastNameText.getText().trim();
                String email = emailText.getText().trim();
                //TODO:add regex to verify email field is .....@...com/org/etc..

                signUpRequest = new SignUpRequest(username, password, firstName, lastName, email);
                //ClientSend.getInstance().sendMessage("SignUpRequest", request);
                sendMessage();
            }
        });
    }

    private void sendMessage(){
        if(!signUpRequest.equals(null)){
            ClientSend.getInstance().sendMessage("SignUpRequest", signUpRequest);
        }
        else{
            System.out.println("NULL MESSAGE");
        }
    }

}
