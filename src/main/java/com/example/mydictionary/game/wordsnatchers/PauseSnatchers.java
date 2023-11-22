package com.example.mydictionary.game.wordsnatchers;

import com.example.mydictionary.AppUtils;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;

public class PauseSnatchers extends UtilsSnatchers {

    @FXML
    public void resumeGame(ActionEvent event){
        timeline.play();
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) mediaPlayer.play();
        playAnchorPane.getChildren().remove(component);
    }

    @FXML
    public void restartGame(ActionEvent event) throws IOException {
        timeline.stop();
        timeline = new Timeline();
        currentTime = time;

        playAnchorPane.getChildren().remove(component);
        snatchersAnchorPane.getChildren().remove(playAnchorPane);

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("game/wordsnatchers/view/play.fxml");
        fxmlLoader.setLocation(url);
        try {
            playAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(playAnchorPane, top1);
            AnchorPane.setLeftAnchor(playAnchorPane, left1);
            snatchersAnchorPane.getChildren().add(playAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
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
            playAnchorPane.getChildren().remove(component);
            snatchersAnchorPane.getChildren().remove(playAnchorPane);
            rootAnchorPane.getChildren().remove(snatchersAnchorPane);
        }
    }
}
