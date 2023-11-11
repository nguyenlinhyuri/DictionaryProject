package com.example.mydictionary.game.Hangman;

import com.example.mydictionary.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class StartGame extends UtilsGame{
    @FXML
    private Button playButton;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button howToPlayButon;

    @FXML
    public void playGame(ActionEvent event) throws IOException {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("game/hangman/view/play.fxml");
        fxmlLoader.setLocation(url);
        try {
            playAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(playAnchorPane, top1);
            AnchorPane.setLeftAnchor(playAnchorPane, left1);
            hangmanAnchorPane.getChildren().add(playAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void howToPlay(ActionEvent event) throws IOException {
        if (mediaPlayer != null) mediaPlayer.stop();
        try {
            component = FXMLLoader.load(getClass().getResource("view/howToPlay.fxml"));
            AnchorPane.setTopAnchor(component, top1);
            AnchorPane.setLeftAnchor(component, left1);
            hangmanAnchorPane.getChildren().add((Node) component);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
