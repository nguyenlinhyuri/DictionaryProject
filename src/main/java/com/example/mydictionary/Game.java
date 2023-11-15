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

    @FXML
    private Button back;

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
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("game/wordbubbles/view/start.fxml");
        fxmlLoader.setLocation(url);
        try {
            bubblesAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(bubblesAnchorPane, top1);
            AnchorPane.setLeftAnchor(bubblesAnchorPane, left1);
            rootAnchorPane.getChildren().add(bubblesAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * chơi word snatchers
     */
    public void wordsnatchersAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("game/wordsnatchers/view/start.fxml");
        fxmlLoader.setLocation(url);
        try {
            snatchersAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(snatchersAnchorPane, top1);
            AnchorPane.setLeftAnchor(snatchersAnchorPane, left1);
            rootAnchorPane.getChildren().add(snatchersAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * back
     */
    @FXML
    public void backAction(ActionEvent event){
        System.out.println("==========================");
        rootAnchorPane.getChildren().remove(gameAnchorPane);
        isGameScene = false;
    }
}
