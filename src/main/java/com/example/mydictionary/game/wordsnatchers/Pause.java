package com.example.mydictionary.game.wordsnatchers;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class Pause extends Utils{
    @FXML
    private Button resumeButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button quitButton;


    public void resumeGame(ActionEvent event){
        timeline.play();
        rootAnchorPane.getChildren().remove(component);
    }

    public void restartGame(ActionEvent event) throws IOException {
        timeline.stop();
        timeline = new Timeline();
        currentTime = time;

        showNewScene(rootAnchorPane, "game/wordsnatchers/view/play.fxml", top, left);

    }

    public void quitGame(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Word Snatcher!");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit the game?");

        if (alert.showAndWait().get() == ButtonType.OK){
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            if (timeline != null) {
                timeline.stop();
            }
            showNewScene(rootAnchorPane, "view/game.fxml", top, left);
        }
    }
}
