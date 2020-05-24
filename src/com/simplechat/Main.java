package com.simplechat;

import com.simplechat.Client.Client;
import com.simplechat.UI.Landing.LandingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Client client = new Client();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UI/Landing/Landing.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));

        LandingController landingController = loader.getController();
        landingController.setClose();

        client.addObserver(landingController);

        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }


}
