package com.example.mydictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

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
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("game/hangman/view/start.fxml");
        fxmlLoader.setLocation(url);
        try {
            hangmanAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(hangmanAnchorPane, top1);
            AnchorPane.setLeftAnchor(hangmanAnchorPane, left1);
            rootAnchorPane.getChildren().add(hangmanAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }

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
