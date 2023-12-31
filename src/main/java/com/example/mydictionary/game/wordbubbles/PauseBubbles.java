package com.example.mydictionary.game.wordbubbles;

import com.example.mydictionary.AppUtils;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;

public class PauseBubbles extends UtilsBubbles {


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
            playAnchorPane.getChildren().remove(component);
            bubblesAnchorPane.getChildren().remove(playAnchorPane);
            rootAnchorPane.getChildren().remove(bubblesAnchorPane);

        }
    }

    @FXML
    public void restartGame(ActionEvent event) {
        mediaPlayer.stop();
        timeline.stop();
        timeline = new Timeline();

        playAnchorPane.getChildren().remove(component);
        bubblesAnchorPane.getChildren().remove(playAnchorPane);

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("game/wordbubbles/view/playGame.fxml");
        fxmlLoader.setLocation(url);
        try {
            playAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(playAnchorPane, top1);
            AnchorPane.setLeftAnchor(playAnchorPane, left1);
            bubblesAnchorPane.getChildren().add(playAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void resumeGame(ActionEvent event) {
        timeline.play();
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) mediaPlayer.play();
        playAnchorPane.getChildren().remove(component);
    }
}
