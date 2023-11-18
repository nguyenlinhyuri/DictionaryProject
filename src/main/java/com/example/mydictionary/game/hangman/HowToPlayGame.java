package com.example.mydictionary.game.hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HowToPlayGame extends UtilsGame{
    public void back(ActionEvent e) throws IOException {
        hangmanAnchorPane.getChildren().remove(component);
    }
}

