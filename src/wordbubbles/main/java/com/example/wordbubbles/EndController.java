package com.example.wordbubbles;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndController extends GameUtils implements Initializable {
    @FXML
    private Label endWordLabel;

    @FXML
    private Label endPointLabel;

    @FXML
    private Button replayButton;

    @FXML
    private Button playMoreGameButton;


    @FXML
    public void replayGame(ActionEvent event) throws IOException {
        root  = FXMLLoader.load(getClass().getResource("start.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void playMoreGame(ActionEvent event){
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        endWordLabel.setText("Word: " + numOfWord);
        endPointLabel.setText("Point: " + point);
    }
}
