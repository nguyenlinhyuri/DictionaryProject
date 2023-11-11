package com.example.mydictionary.practice;

import com.example.mydictionary.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Exercises extends AppUtils {
    @FXML
    private Button backButton;

    private int score = 0;

    public void backAction(ActionEvent event){
        if (score == 0){
            showNewScene(rootAnchorPane, "view/practice.fxml", top, left);
        } else {

        }
    }
}
