package com.example.mydictionary.game.hangman;

import com.example.mydictionary.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CongratulationGame extends UtilsGame implements Initializable {
    @FXML
    private Button nextButon;

    @FXML
    private Button quitButton;

    @FXML
    private Label wordLabel;

    @FXML
    private ImageView myImage;


    @FXML
    public void next(ActionEvent event) throws Exception {
        playAnchorPane.getChildren().remove(component);
        hangmanAnchorPane.getChildren().remove(playAnchorPane);

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

    @FXML
    public void quitAction(ActionEvent event){
        Alert quitAL = new Alert(Alert.AlertType.CONFIRMATION);
        quitAL.setTitle("Quit Hangman!");
        quitAL.setHeaderText(null);
        quitAL.setContentText("Are you sure you want to quit the game?");
        if (quitAL.showAndWait().get() == ButtonType.OK) {
//            System.exit(0);
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            playAnchorPane.getChildren().remove(component);
            hangmanAnchorPane.getChildren().remove(playAnchorPane);
            rootAnchorPane.getChildren().remove(hangmanAnchorPane);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wordLabel.setText("The word is: " + word);
    }
}