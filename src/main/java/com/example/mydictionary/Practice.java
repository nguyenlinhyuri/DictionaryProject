package com.example.mydictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class Practice extends AppUtils {
    @FXML
    private Button notedWordsButton;

    @FXML
    private Button flashcardsButton;

    @FXML
    private Button exercisesButton;


    /**
     * click xem các từ đã lưu;
     */
    public void notedWordsAction(ActionEvent event){

    }

    /**
     * click flashcards
     */
    public void flashcardsAction(ActionEvent event) throws IOException {
        showNewScene(rootAnchorPane, "practice/flashcard.fxml", top, left);
    }

    /**
     * click làm  bài tập
     */
    public void exercisesAction(ActionEvent event){

    }

}
