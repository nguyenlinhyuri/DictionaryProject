package com.example.mydictionary.practice;

import com.example.mydictionary.*;
import com.example.mydictionary.jdbc.JdbcDao;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;

public class FlashcardController extends AppUtils implements Initializable {
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


    private List<String> wordList = new ArrayList<>();
    private boolean statusOfFlashcard = true;  // mặt trước là tiếng Anh
    private JdbcDao jdbcDao = new JdbcDao();


    /**
     * cập nhật dữ liệu cho wordList
     */
    public void readData() throws SQLException {
        wordList = jdbcDao.getAllWords();
    }

    /**
     * lấy từ tiếp theo chỉ số i
     */
    public String nextWord(int i) {
        int n = wordList.size();
        if (i == n - 1) {
            return wordList.get(0);
        }
        return wordList.get(i + 1);
    }

    /**
     * lấy từ ở trước chỉ số i
     */
    public String prevWord(int i) {
        int n = wordList.size();
        if (i == 0) return wordList.get(n - 1);
        return wordList.get(i - 1);
    }

    /**
     * chi so cua tu hien tai
     */
    public int getCurrentIndex() throws SQLException {
        String cur;
        if (statusOfFlashcard) { // mặt tiếng anh
            cur = wordLabel.getText();
            for (int i = 0; i < wordList.size(); i++) {
                if (cur.equals(wordList.get(i))) {
                    return i;
                }
            }
        } else { // mặt tiếng việt
            cur = wordLabel.getText();
            for (int i = 0; i < wordList.size(); i++) {
                if (cur.equals(jdbcDao.getMeaning(wordList.get(i)))) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void turnFlashcardOver() throws SQLException {
        int currentIndex = getCurrentIndex();

        String currentWord = wordList.get(currentIndex);

        if (statusOfFlashcard) { // en
            wordLabel.setText(jdbcDao.getMeaning(currentWord));
            statusOfFlashcard = false;
        } else { // vi
            wordLabel.setText(currentWord);
            statusOfFlashcard = true;
        }
    }

    public void prevFlashcard() throws SQLException {
        int currentIndex = getCurrentIndex();

        String prevWord = prevWord(currentIndex);
        if (prevWord != null) {
            wordLabel.setText(prevWord);
            statusOfFlashcard = true;
        }
    }

    public void nextFlashcard() throws SQLException {
        int currentIndex = getCurrentIndex();

        String nextWord = nextWord(currentIndex);
        if (nextWord != null) {
            wordLabel.setText(nextWord);
            statusOfFlashcard = true;
        }
    }

    @FXML
    public void handleKeyPressed(KeyEvent event) throws SQLException {
        switch (event.getCode()) {
            case F -> nextFlashcard();
            case A -> prevFlashcard();
            case S -> turnFlashcardOver();
        }
    }

    @FXML
    public void backAction(ActionEvent event) throws IOException {
        practiceAnchorPane.getChildren().remove(flashcardAnchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!wordList.isEmpty()){
            String firstWord = wordList.get(0);
            wordLabel.setText(firstWord);
        }
    }
}
