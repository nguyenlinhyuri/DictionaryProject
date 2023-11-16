package com.example.mydictionary.practice;

import com.example.mydictionary.Practice;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CongratulationExercises extends Practice implements Initializable{
    @FXML
    private Label congratulationLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button practiceAgainButton;

    /**
     * quay lại
     */
    public void backAction(ActionEvent event){
        exercisesAnchorPane.getChildren().remove(endexercisesAnchorPane);
        startexercisesAnchorPane.getChildren().remove(exercisesAnchorPane);
        practiceAnchorPane.getChildren().remove(startexercisesAnchorPane);
    }

    /**
     * luyện tập lại
     */
    public void practiceAgainAction(ActionEvent event){
        exercisesAnchorPane.getChildren().remove(endexercisesAnchorPane);
        startexercisesAnchorPane.getChildren().remove(exercisesAnchorPane);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (numOfWordsPracticed == 1) congratulationLabel.setText(numOfWordsPracticed + "word");
        congratulationLabel.setText(numOfWordsPracticed + " words");
    }
}
