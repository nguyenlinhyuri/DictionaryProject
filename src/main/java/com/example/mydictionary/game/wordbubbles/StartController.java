package com.example.mydictionary.game.wordbubbles;

import com.example.mydictionary.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class StartController extends GameUtils implements Initializable {
    @FXML
    private Button playButton;

    @FXML
    private Button howToPlayButton;

    @FXML
    private Button backButton;

    public void howToPlayGame(ActionEvent event) throws IOException {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        try {
            component = FXMLLoader.load(getClass().getResource("view/howToPlay.fxml"));
            AnchorPane.setTopAnchor(component, top1);
            AnchorPane.setLeftAnchor(component, left1);
            bubblesAnchorPane.getChildren().add((Node) component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backGame(ActionEvent event) throws IOException {
       bubblesAnchorPane.getChildren().remove(component);
    }

    public void playGame(ActionEvent event) throws IOException {
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("game/wordbubbles/view/playGame.fxml");
        fxmlLoader.setLocation(url);
        try {
            playAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(playAnchorPane, top1);
            AnchorPane.setLeftAnchor(playAnchorPane, left1);
            bubblesAnchorPane.getChildren().add(playAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playSound("sound/start.wav", 1);
    }

}
