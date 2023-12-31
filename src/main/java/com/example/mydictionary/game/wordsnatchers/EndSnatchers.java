package com.example.mydictionary.game.wordsnatchers;

import com.example.mydictionary.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndSnatchers extends UtilsSnatchers implements Initializable {
    @FXML
    private Label endScoreLabel;
    @FXML
    public void replayGame(ActionEvent event) throws IOException {
        playAnchorPane.getChildren().remove(endAnchorPane);
        snatchersAnchorPane.getChildren().remove(playAnchorPane);
        rootAnchorPane.getChildren().remove(snatchersAnchorPane);

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

    @FXML
    public void playMoreGame(ActionEvent event){
        if (mediaPlayer != null) mediaPlayer.stop();

        playAnchorPane.getChildren().remove(endAnchorPane);
        snatchersAnchorPane.getChildren().remove(playAnchorPane);
        rootAnchorPane.getChildren().remove(snatchersAnchorPane);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playSound("sound/end.wav", 1);
        endScoreLabel.setText("Points " + point);
    }
}
