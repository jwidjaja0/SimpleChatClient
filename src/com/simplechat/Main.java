package com.simplechat;

import com.simplechat.Client.Client;
import com.simplechat.UI.Landing.LandingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("UI/Landing/Landing.fxml"));
        //com\simplechat\UI\Landing\Landing.fxml
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));

        LandingController landingController = loader.getController();
        landingController.setClose();

        primaryStage.show();

        Client client = null;
        try {
            client = new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("adding observer");
        client.addObserver(landingController);
    }

    public static void main(String[] args) {

        launch(args);
    }


}
