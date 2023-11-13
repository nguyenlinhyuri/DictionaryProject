package com.example.mydictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image(getClass().getResourceAsStream("image/icon.png"));
        stage.getIcons().add(icon);
        Parent root = FXMLLoader.load(Main.class.getResource("view/login.fxml"));
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("My Dictionary");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}