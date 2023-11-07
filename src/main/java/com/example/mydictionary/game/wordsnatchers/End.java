package com.example.mydictionary.game.wordsnatchers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class End extends Utils implements Initializable {
    @FXML
    private Label endScoreLabel;

    @FXML
    private Button replayButton;

    @FXML
    private Button playMoreGameButton;


    @FXML
    public void replayGame(ActionEvent event) throws IOException {
        showNewScene(rootAnchorPane, "game/wordsnatchers/view/start.fxml", top, left);
    }

    @FXML
    public void playMoreGame(ActionEvent event){
        showNewScene(rootAnchorPane, "view/game.fxml", top, left);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playSound("sound/end.wav", 1);
        endScoreLabel.setText("Points " + point);
    }
}
