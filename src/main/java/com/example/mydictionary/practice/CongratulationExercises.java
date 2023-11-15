package com.example.mydictionary.practice;

import com.example.mydictionary.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CongratulationExercises extends AppUtils {
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
}
