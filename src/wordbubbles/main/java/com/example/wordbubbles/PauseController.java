package com.example.wordbubbles;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class PauseController extends GameUtils{
    @FXML
    private Button resumeButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button quitButton;

    @FXML
    public void quitGame(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Word Bubbles!");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit the game?");
        if (alert.showAndWait().get() == ButtonType.OK){
            System.exit(0);  // thoát game
        }
    }

    @FXML
    public void restartGame(ActionEvent event) throws IOException {
        timeline.stop();
        timeline = new Timeline();
        root  = FXMLLoader.load(getClass().getResource("playGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void resumeGame(ActionEvent event) throws IOException {
        root  = FXMLLoader.load(getClass().getResource("playGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }
}
