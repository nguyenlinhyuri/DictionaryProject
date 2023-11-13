package com.example.mydictionary;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

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
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("practice/notedWord.fxml");
        fxmlLoader.setLocation(url);
        try {
            notedwordAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(notedwordAnchorPane, top);
            AnchorPane.setLeftAnchor(notedwordAnchorPane, left);
            rootAnchorPane.getChildren().add(notedwordAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * click flashcards
     */
    public void flashcardsAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("practice/flashcard.fxml");
        fxmlLoader.setLocation(url);
        try {
            flashcardAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(flashcardAnchorPane, top);
            AnchorPane.setLeftAnchor(flashcardAnchorPane, left);
            rootAnchorPane.getChildren().add(flashcardAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * click làm  bài tập
     */
    public void exercisesAction(ActionEvent event){
        showNewScene(rootAnchorPane, "practice/exercises.fxml", top, left);
    }

}
