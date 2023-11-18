package com.example.mydictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public AppUtils utils = new AppUtils();
    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image(getClass().getResourceAsStream("image/icon.png"));
        stage.getIcons().add(icon);
        Parent root = FXMLLoader.load(Main.class.getResource("view/login.fxml"));
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("EnglishPro");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            try {
                utils.writeNotedWordData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}