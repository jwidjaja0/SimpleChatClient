package sample.UI.Alert;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;

public class ExitAlertController {
    @FXML private Button yesButton;
    @FXML private Button noButton;

    private boolean answer = false;

    public void initialize(){
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                answer = true;
            }
        });
    }

    public boolean display(){
        return false;
    }
}
