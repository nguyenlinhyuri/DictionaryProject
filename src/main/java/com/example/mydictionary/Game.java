package com.example.mydictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Game extends AppUtils {
    @FXML
    private Button hangMan;

    @FXML
    private Button wordBubbles;

    @FXML
    private Button wordSnatchers;

    /**
     * chơi hangman
     */
    public void hangManAction(ActionEvent event) {

    }

    /**
     * chơi word bubbles
     */
    public void wordbubblesAction(ActionEvent event) {
        showNewScene(rootAnchorPane, "game/wordbubbles/view/start.fxml", top, left);
    }

    /**
     * chơi word snatchers
     */
    public void wordsnatchersAction(ActionEvent event) {
        showNewScene(rootAnchorPane, "game/wordsnatchers/view/start.fxml", top, left);
        System.out.println(rootAnchorPane);
    }
}
