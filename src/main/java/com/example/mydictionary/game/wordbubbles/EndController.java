package com.example.mydictionary.game.wordbubbles;

import com.example.mydictionary.AppUtils;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.*;
import java.util.*;

public class EndController extends GameUtils implements Initializable {
    @FXML
    private Label endWordLabel;

    @FXML
    private Label endPointLabel;




    @FXML
    public void replayGame(ActionEvent event) throws IOException {
        playAnchorPane.getChildren().remove(endAnchorPane);
        bubblesAnchorPane.getChildren().remove(playAnchorPane);
        rootAnchorPane.getChildren().remove(bubblesAnchorPane);

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

    @FXML
    public void playMoreGame(ActionEvent event){
        if (mediaPlayer != null) mediaPlayer.stop();

        playAnchorPane.getChildren().remove(endAnchorPane);
        bubblesAnchorPane.getChildren().remove(playAnchorPane);
        rootAnchorPane.getChildren().remove(bubblesAnchorPane);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playSound("sound/end.wav", 1);
        endWordLabel.setText("Words: " + numOfWord);
        endPointLabel.setText("Points: " + point);
    }
}
