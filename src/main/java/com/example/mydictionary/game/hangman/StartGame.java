package com.example.mydictionary.game.hangman;

import com.example.mydictionary.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartGame extends UtilsGame implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playSound("sound/music1.mp3", 3);
    }
}
