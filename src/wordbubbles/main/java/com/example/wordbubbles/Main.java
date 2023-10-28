package com.example.wordbubbles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        Scene scene = new Scene(root, 800, 520);
//        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Word Bubbles!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}