package com.example.wordsnatcher;

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


    public void resumeGame(ActionEvent event){

    }

    public void restartGame(ActionEvent event) throws IOException {
        timeline = new Timeline();
        root  = FXMLLoader.load(getClass().getResource("play.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void quitGame(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Word Snatcher!");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit the game?");

        if (alert.showAndWait().get() == ButtonType.OK){
            System.exit(0);  // thoát game
        }
    }
}
