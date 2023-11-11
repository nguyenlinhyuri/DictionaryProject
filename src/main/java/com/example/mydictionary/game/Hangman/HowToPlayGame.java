package com.example.mydictionary.game.Hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HowToPlayGame extends UtilsGame{
    @FXML
    private Button backButon;


    public void back(ActionEvent e) throws IOException {
        hangmanAnchorPane.getChildren().remove(component);
    }
}

