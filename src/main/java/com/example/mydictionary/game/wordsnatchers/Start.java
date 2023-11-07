package com.example.mydictionary.game.wordsnatchers;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Start extends Utils implements Initializable {
    @FXML
    private Button playButton;

    @FXML
    private Button howToPlayButton;

    @FXML
    private Button backButton;

    public void howToPlayGame(ActionEvent event) throws IOException {
//        System.out.println(rootAnchorPane);
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        showNewScene(rootAnchorPane, "game/wordsnatchers/view/howToPlay.fxml", top, left);
    }

    public void backGame(ActionEvent event) throws IOException {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        showNewScene(rootAnchorPane, "game/wordsnatchers/view/start.fxml", top, left);
    }

    public void playGame(ActionEvent event) throws IOException {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        showNewScene(rootAnchorPane, "game/wordsnatchers/view/play.fxml", top, left);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playSound("sound/start.wav", 1);
    }
}
