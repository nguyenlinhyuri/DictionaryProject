package com.example.mydictionary;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;

public class Practice extends AppUtils {
    @FXML
    private Button notedWordsButton;

    @FXML
    private Button flashcardsButton;

    @FXML
    private Button exercisesButton;

    @FXML
    private Button backButton;

//    public static AnchorPane practiceAnchorPane;
    public static AnchorPane flashcardAnchorPane;
    public static AnchorPane notedwordAnchorPane;
    public static AnchorPane startexercisesAnchorPane;
    public static AnchorPane endexercisesAnchorPane;
    public static AnchorPane exercisesAnchorPane;


    /**
     * click xem các từ đã lưu;
     */
    public void notedWordsAction(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("practice/notedWord.fxml");
        fxmlLoader.setLocation(url);
        try {
            notedwordAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(notedwordAnchorPane, top1);
            AnchorPane.setLeftAnchor(notedwordAnchorPane, left1);
            practiceAnchorPane.getChildren().add(notedwordAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * click flashcards
     */
    public void flashcardsAction(ActionEvent event) throws IOException {
        if (notedWord.isEmpty()){
            showAlertInformation("Information", "You have not had the word noted.");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = AppUtils.class.getResource("practice/flashcard.fxml");
            fxmlLoader.setLocation(url);
            try {
                flashcardAnchorPane = fxmlLoader.load();
                AnchorPane.setTopAnchor(flashcardAnchorPane, top1);
                AnchorPane.setLeftAnchor(flashcardAnchorPane, left1);
                practiceAnchorPane.getChildren().add(flashcardAnchorPane);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * click làm  bài tập
     */
    public void exercisesAction(ActionEvent event){
        if (notedWord.isEmpty()){
            showAlertInformation("Information", "You have not had the word noted.");
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = AppUtils.class.getResource("practice/startexercises.fxml");
            fxmlLoader.setLocation(url);
            try {
                startexercisesAnchorPane = fxmlLoader.load();
                AnchorPane.setTopAnchor(startexercisesAnchorPane, top1);
                AnchorPane.setLeftAnchor(startexercisesAnchorPane, left1);
                practiceAnchorPane.getChildren().add(startexercisesAnchorPane);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * back
     */
    public void backAction(ActionEvent event){
        rootAnchorPane.getChildren().remove(practiceAnchorPane);
        isPracticeScene = false;
    }
}
