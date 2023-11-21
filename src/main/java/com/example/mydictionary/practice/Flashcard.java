package com.example.mydictionary.practice;

import com.example.mydictionary.*;
import com.example.mydictionary.basic.Word;
import javafx.animation.TranslateTransition;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.*;
import java.sql.SQLException;
import java.util.*;

public class Flashcard extends Practice implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button turnButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    private Button backButton;

    //-----------------
    @FXML
    private Label wordLabel;

    @FXML
    private VBox flashcardBox;

    @FXML
    private TextArea meaningTextArea;



    private List<Word> wordList = new ArrayList<>();
    private boolean statusOfFlashcard = true;  // mặt trước là tiếng Anh


    /**
     * cập nhật dữ liệu cho wordList
     */
    public void readData() throws SQLException {
        for (Map.Entry entry : notedWord.entrySet()) {
            wordList.add(new Word((String) entry.getKey(), (String) entry.getValue()));
        }
    }

    /**
     * lấy từ tiếp theo chỉ số i
     */
    public Word nextWord(int i) {
        int n = wordList.size();
        if (i == n - 1) {
            return wordList.get(0);
        }
        return wordList.get(i + 1);
    }

    /**
     * lấy từ ở trước chỉ số i
     */
    public Word prevWord(int i) {
        int n = wordList.size();
        if (i == 0) return wordList.get(n - 1);
        return wordList.get(i - 1);
    }

    /**
     * chi so cua tu hien tai
     */
    public int getCurrentIndex() {
        String cur = wordLabel.getText();
        for (int i=0; i<wordList.size(); i++){
            if (cur.equals(wordList.get(i).getTarget())) return i;
        }
        return -1;
    }

    public void turnFlashcardOver() {
        int currentIndex = getCurrentIndex();
        Word currentWord = wordList.get(currentIndex);

        if (statusOfFlashcard) { // en
            wordLabel.setText(currentWord.getTarget());
            meaningTextArea.setText(currentWord.getExplain());
            statusOfFlashcard = false;
        } else { // vi
            meaningTextArea.clear();
            statusOfFlashcard = true;
        }
    }

    public void prevFlashcard() {
        int currentIndex = getCurrentIndex();
        Word prevWord = prevWord(currentIndex);
        if (prevWord != null) {
            wordLabel.setText(prevWord.getTarget());
            meaningTextArea.clear();
            statusOfFlashcard = true;
        }
        double startX = -50.0;
        double startY = 0.0;

        double endX = 0.0;
        double endY = 0.0;

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), flashcardBox);
        transition.setFromX(startX);
        transition.setFromY(startY);
        transition.setToX(endX);
        transition.setToY(endY);
        transition.play();
    }

    public void nextFlashcard() {
        int currentIndex = getCurrentIndex();
        Word nextWord = nextWord(currentIndex);
        if (nextWord != null) {
            wordLabel.setText(nextWord.getTarget());
            meaningTextArea.clear();
            statusOfFlashcard = true;
        }
        double startX = 50.0;
        double startY = 0.0;

        double endX = 0.0;
        double endY = 0.0;

        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), flashcardBox);
        transition.setFromX(startX);
        transition.setFromY(startY);
        transition.setToX(endX);
        transition.setToY(endY);
        transition.play();
    }

    @FXML
    public void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case F -> nextFlashcard();
            case A -> prevFlashcard();
            case S -> turnFlashcardOver();
        }
    }

    @FXML
    public void backAction(ActionEvent event) {
        practiceAnchorPane.getChildren().remove(flashcardAnchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        meaningTextArea.setWrapText(true);
        meaningTextArea.setEditable(false);

        if (!wordList.isEmpty()) {
            Word firstWord = wordList.get(0);
            wordLabel.setText(firstWord.getTarget());
        }
    }
}
