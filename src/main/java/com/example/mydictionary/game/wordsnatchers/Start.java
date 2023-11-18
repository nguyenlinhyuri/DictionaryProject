package com.example.mydictionary.game.wordsnatchers;

import com.example.mydictionary.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Start extends Utils implements Initializable {

    public void howToPlayGame(ActionEvent event) throws IOException {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        try {
            component = FXMLLoader.load(getClass().getResource("view/howToPlay.fxml"));
            AnchorPane.setTopAnchor(component, top1);
            AnchorPane.setLeftAnchor(component, left1);
            snatchersAnchorPane.getChildren().add((Node) component);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void backGame(ActionEvent event) throws IOException {
        snatchersAnchorPane.getChildren().remove(component);
    }

    public void playGame(ActionEvent event) throws IOException {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("game/wordsnatchers/view/play.fxml");
        fxmlLoader.setLocation(url);
        try {
            playAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(playAnchorPane, top1);
            AnchorPane.setLeftAnchor(playAnchorPane, left1);
            snatchersAnchorPane.getChildren().add(playAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playSound("sound/start.wav", 1);
    }
}
