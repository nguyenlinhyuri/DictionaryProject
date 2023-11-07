package com.example.mydictionary.game.wordbubbles;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class EndController extends GameUtils implements Initializable {
    @FXML
    private Label endWordLabel;

    @FXML
    private Label endPointLabel;

    @FXML
    private Button replayButton;

    @FXML
    private Button playMoreGameButton;


    @FXML
    public void replayGame(ActionEvent event) throws IOException {
        showNewScene(rootAnchorPane, "game/wordbubbles/view/start.fxml", top, left);
    }

    @FXML
    public void playMoreGame(ActionEvent event){
        showNewScene(rootAnchorPane, "view/game.fxml", top, left);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playSound("sound/end.wav", 1);
        endWordLabel.setText("Words: " + numOfWord);
        endPointLabel.setText("Points: " + point);
    }
}
