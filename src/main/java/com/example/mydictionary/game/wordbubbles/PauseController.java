package com.example.mydictionary.game.wordbubbles;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.io.IOException;

public class PauseController extends GameUtils {
    @FXML
    private Button resumeButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button quitButton;

    @FXML
    public void quitGame(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit Word Bubbles!");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit the game?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            if (timeline != null) {
                timeline.stop();
            }
            showNewScene(rootAnchorPane, "view/game.fxml", top, left);
        }
    }

    @FXML
    public void restartGame(ActionEvent event) throws IOException {
        mediaPlayer.stop();
        timeline.stop();
        timeline = new Timeline();

        showNewScene(rootAnchorPane, "game/wordbubbles/view/playGame.fxml", top, left);
    }

    @FXML
    public void resumeGame(ActionEvent event) throws IOException {
        timeline.play();
        rootAnchorPane.getChildren().remove(component);
    }
}
