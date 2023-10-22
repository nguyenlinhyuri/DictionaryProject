package com.example.flashcard;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.ResourceBundle;

class Word {
    private String target;
    private String explain;

    public Word(String target, String explain) {
        this.target = target;
        this.explain = explain;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}

public class FlashcardController implements Initializable{
    @FXML
    private Button turnButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;

    @FXML
    private Label wordLabel;

    private ArrayList<Word> wordList = new ArrayList<>();
    private final String DATA_PATH = "E:\\Java\\intellijJava\\OOPtemp\\Flashcard\\src\\main\\resources\\com\\example\\flashcard\\data.txt";
    private boolean statusOfFlashcard = true;  // mặt trước là tiếng Anh

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        wordLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, null, null)));
        if (!wordList.isEmpty()) {
            Word firstWord = wordList.get(0);
            wordLabel.setText(firstWord.getTarget());
        }
    }

    /**
     * cập nhật dữ liệu cho wordList
     */
    public void readData() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(DATA_PATH));
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
    public int getCurrentIndex(){
        String cur;
        if (statusOfFlashcard){ // mặt tiếng anh
            cur = wordLabel.getText();
            for (int i=0; i< wordList.size(); i++){
                if (cur.equals(wordList.get(i).getTarget())){
                    return i;
                }
            }
        } else { // mặt tiếng việt
            cur = wordLabel.getText();
            for(int i=0; i<wordList.size(); i++){
                if (cur.equals(wordList.get(i).getExplain())){
                    return i;
                }
            }

        }

        return -1;
    }


    @FXML
    public void turnFlashcardOver() {
        int currentIndex = getCurrentIndex();
//        System.out.println(currentIndex);
        Word currentWord = wordList.get(currentIndex);

        if (statusOfFlashcard) { // en
            wordLabel.setText(currentWord.getExplain());
            statusOfFlashcard = false;
        } else { // vi
            wordLabel.setText(currentWord.getTarget());
            statusOfFlashcard = true;
        }
    }

    @FXML
    public void prevFlashcard() {
        int currentIndex = getCurrentIndex();
        System.out.println(currentIndex);
        Word prevWord = prevWord(currentIndex);
        wordLabel.setText(prevWord.getTarget());
        statusOfFlashcard = true;
    }

    @FXML
    public void nextFlashcard() {
        int currentIndex = getCurrentIndex();
//        System.out.println(currentIndex);
        Word nextWord = nextWord(currentIndex);
        wordLabel.setText(nextWord.getTarget());
        statusOfFlashcard = true;
    }

    @FXML
    public void keyPressedNext(KeyEvent event){
        if (event.getCode() == KeyCode.RIGHT){
            nextFlashcard();
        }
    }

    @FXML
    public void keyPressedPrev(KeyEvent event){
        if (event.getCode() == KeyCode.LEFT){
            prevFlashcard();
        }
    }

    @FXML
    public void keyPressedTurn(KeyEvent event){
        if (event.getCode() == KeyCode.SPACE){
            turnFlashcardOver();
        }
    }
}