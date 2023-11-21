package com.example.mydictionary.game.hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HowToPlayGame extends UtilsGame implements Initializable {
    public void back(ActionEvent e) throws IOException {
        hangmanAnchorPane.getChildren().remove(component);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playSound("soung/music1.mp3" , 3);
    }
}

