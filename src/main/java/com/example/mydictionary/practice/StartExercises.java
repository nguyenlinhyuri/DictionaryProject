package com.example.mydictionary.practice;

import com.example.mydictionary.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class StartExercises extends AppUtils {
    @FXML
    private Button backButton;

    @FXML
    private Button letpracticeButton;


    /**
     * quay lại
     */
    public void backAction(ActionEvent event) {
        practiceAnchorPane.getChildren().remove(startexercisesAnchorPane);
    }


    /**
     * ấn practice
     */
    public void letPracticeAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("practice/exercises.fxml");
        fxmlLoader.setLocation(url);
        try {
            exercisesAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(exercisesAnchorPane, top1);
            AnchorPane.setLeftAnchor(exercisesAnchorPane, left1);
            startexercisesAnchorPane.getChildren().add(exercisesAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
