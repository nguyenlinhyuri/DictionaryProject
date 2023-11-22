package com.example.mydictionary.game.hangman;

import com.example.mydictionary.AppUtils;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoseGame extends UtilsGame implements Initializable {


    @FXML
    private Label tagetLB;

    @FXML
    private ImageView myImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (mediaPlayer != null) mediaPlayer.stop();
        playSound("sound/lose.mp3" , 10);
        tagetLB.setText("This word is : " + word); //+ //playController.getWord());
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(myImage);
        scale.setDuration(Duration.millis(1000));
        //scale.setCycleCount(TranslateTransition.INDEFINITE);
        // scale.setCycleCount(1);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(15.0);
        scale.setByY(15.0);
        //scale.setAutoReverse(true);
        scale.play();

    }


    @FXML
    void quitgame(ActionEvent event) {
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

    @FXML
    void replay(ActionEvent event) throws IOException {

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
}
