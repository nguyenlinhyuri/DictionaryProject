package com.example.mydictionary.game.wordbubbles;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;

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
        showNewScene(rootAnchorPane, "game/wordbubbles/view/howToPlay.fxml", top, left);
    }

    public void backGame(ActionEvent event) throws IOException {
        showNewScene(rootAnchorPane, "game/wordbubbles/view/start.fxml", top, left);
    }

    public void playGame(ActionEvent event) throws IOException {
        if (mediaPlayer != null) mediaPlayer.stop();
//        rootAnchorPane = FXMLLoader.load(getClass().getResource("view/playGame.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(rootAnchorPane);
//        stage.setScene(scene);
//        stage.show();
        showNewScene(rootAnchorPane, "game/wordbubbles/view/playGame.fxml", top, left);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playSound("game/wordbubbles/sound/start.wav", 1);
        System.out.println("pppppppppppppp");
    }

}
