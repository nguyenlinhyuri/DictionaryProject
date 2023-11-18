package com.example.mydictionary.practice;

import com.example.mydictionary.*;
import com.example.mydictionary.basic.Word;
import javafx.animation.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.*;
import java.sql.SQLException;
import java.util.*;

public class FlashcardController extends Practice implements Initializable {
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

    @FXML
    private Label wordLabel;


    private List<Word> wordList = new ArrayList<>();
    private boolean statusOfFlashcard = true;  // mặt trước là tiếng Anh
//    private JdbcDao jdbcDao = new JdbcDao();


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
        String cur;
        if (statusOfFlashcard) { // mặt tiếng anh
            cur = wordLabel.getText();
            for (int i = 0; i < wordList.size(); i++) {
                if (cur.equals(wordList.get(i).getTarget())) {
                    return i;
                }
            }
        } else { // mặt tiếng việt
            cur = wordLabel.getText();
            for (int i = 0; i < wordList.size(); i++) {
                if (cur.equals(wordList.get(i).getExplain())) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * lật label
     */
    public void LabelFlip(){
        wordLabel.setMinSize(50, 326);
        RotateTransition flip = new RotateTransition(Duration.seconds(0.5), wordLabel);
        flip.setByAngle(180);
        flip.setAxis(Rotate.Y_AXIS);
        flip.setOnFinished(event -> {
            wordLabel.setRotate(0); // Đặt góc xoay của chữ trong Label về 0
        });
        flip.play();
    }

    public void turnFlashcardOver() {
        int currentIndex = getCurrentIndex();
        Word currentWord = wordList.get(currentIndex);

        if (statusOfFlashcard) { // en
            LabelFlip();
            PauseTransition delay = new PauseTransition(Duration.seconds(0.25));
            delay.setOnFinished(e -> {
                wordLabel.setText(currentWord.getExplain());
                statusOfFlashcard = false;
            });
            delay.play();
        } else { // vi
            LabelFlip();
            PauseTransition delay = new PauseTransition(Duration.seconds(0.25));
            delay.setOnFinished(e -> {
                wordLabel.setText(currentWord.getTarget());
                statusOfFlashcard = true;
            });
            delay.play();
        }
    }

    public void prevFlashcard() {
        int currentIndex = getCurrentIndex();
        Word prevWord = prevWord(currentIndex);
        if (prevWord != null) {
            wordLabel.setText(prevWord.getTarget());
            statusOfFlashcard = true;
        }
    }

    public void nextFlashcard() {
        int currentIndex = getCurrentIndex();
        Word nextWord = nextWord(currentIndex);
        if (nextWord != null) {
            wordLabel.setText(nextWord.getTarget());
            statusOfFlashcard = true;
        }
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


        if (!wordList.isEmpty()) {
            Word firstWord = wordList.get(0);
            wordLabel.setText(firstWord.getTarget());
        }
    }
}
