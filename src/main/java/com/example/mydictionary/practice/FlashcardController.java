package com.example.mydictionary.practice;

import com.example.mydictionary.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import java.io.*;
import java.net.*;
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

    private ArrayList<Word> wordList = new ArrayList<>();
    private boolean statusOfFlashcard = true;  // mặt trước là tiếng Anh


    /**
     * cập nhật dữ liệu cho wordList
     */
    public void readData() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(NOTED_WORD_PATH));
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] parts = line.split("\t");
            if (parts.length >= 2) {
                wordList.add(new Word(parts[0], parts[1]));
            }
        }
    }

    /**
     * lấy từ tiếp theo chỉ số i
     */
    public Word nextWord(int i) {
        int n = wordList.size();
        if (i == n - 1) return wordList.get(0);
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

    public void turnFlashcardOver() {
        int currentIndex = getCurrentIndex();
        Word currentWord = wordList.get(currentIndex);

        if (statusOfFlashcard) { // en
            wordLabel.setText(currentWord.getExplain());
            statusOfFlashcard = false;
        } else { // vi
            wordLabel.setText(currentWord.getTarget());
            statusOfFlashcard = true;
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
    public void backAction(ActionEvent event) throws IOException {
//        showNewScene(rootAnchorPane, "view/practice.fxml", top, left);
        practiceAnchorPane.getChildren().remove(flashcardAnchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (!wordList.isEmpty()) {
            Word firstWord = wordList.get(0);
            wordLabel.setText(firstWord.getTarget());
        }
    }
}
