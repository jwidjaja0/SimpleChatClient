package sample.UI.Login;

import com.SimpleChat.Messages.Login.SignUpFail;
import com.SimpleChat.Messages.Login.SignUpRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.FailHandler;
import sample.OutgoingSingleton;
import sample.UI.Alert.AlertBox;


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
                    //JOptionPane.showMessageDialog(null, "Password not equal");
                    AlertBox.display("Error", "Password not equal");
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
            System.out.println("putting to singleton");
            OutgoingSingleton.getInstance().sendMessage("SignUpRequest", signUpRequest);
        }
        else{
            System.out.println("NULL MESSAGE");
        }
    }

    private void close(){
        Platform.runLater(() -> ((Stage)exitButton.getScene().getWindow()).close());
    }

    public void signupSuccess(){
        Platform.runLater(() -> AlertBox.display("SignUpSuccess", "Success!"));
        close();
    }
    public void signupFail(SignUpFail signUpFail){
        Platform.runLater(() -> AlertBox.display("SignupFail", FailHandler.getSignupFailCause(signUpFail)));
    }

}
