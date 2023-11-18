package com.example.mydictionary.game.hangman;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;
import java.util.*;

public class PlayGame extends UtilsGame implements Initializable {
    @FXML
    private Label commentLB;

    @FXML
    private Label hpLB;

    @FXML
    private TextField myTextField;



    @FXML
    private Label resultLB;

    private int hp;


    private String secretWord;
    private ArrayList<String> enter = new ArrayList<String>();

    public void readInput() throws IOException {
        Scanner sc = new Scanner(new File(path));
        while (sc.hasNext()) {
            data.add(sc.nextLine());
        }
    }

    public void randomWord() {
        Random random = new Random();
        word = data.get(random.nextInt(data.size()));

        commentLB.setText("enter your letter here!");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        word = new String();
        randomWord();
        secretWord = new String();
//        found.clear();
//        wrong.clear();
        enter.clear();
        hp = 7;
        hpLB.setText("Lives:" + hp);
        createSecretWord();

    }


    @FXML
    public void submit(ActionEvent event) throws IOException {
        String letterGuess = myTextField.getText(0, 1);
        if (checkIfEntered(letterGuess, enter)) {
            commentLB.setText("you have already used this letter!");
        } else {
            enter.add(letterGuess);
            if (isCharInWord(letterGuess, word)) {
                updateSecretWord(letterGuess, word);
                resultLB.setText(secretWord);
                commentLB.setText("nice!!");
                //addLetter(letterGuess, found);
            } else {
                hp--;
                if (hp <= 0) {
                    try {
                        component = FXMLLoader.load(getClass().getResource("view/lose.fxml"));
                        AnchorPane.setTopAnchor(component, top1);
                        AnchorPane.setLeftAnchor(component, left1);
                        hangmanAnchorPane.getChildren().add((Node) component);
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    return;
                }
                hpLB.setText("Lives: " + hp);
                commentLB.setText("oh wrong :<  try again !");
                //addLetter(letterGuess, wrong);
            }
            if (secretWord.equals(word)) {

                try {
                    component = FXMLLoader.load(getClass().getResource("view/congratulation.fxml"));
                    AnchorPane.setTopAnchor(component, top1);
                    AnchorPane.setLeftAnchor(component, left1);
                    hangmanAnchorPane.getChildren().add((Node) component);
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
        myTextField.clear();

    }

    @FXML
    public void quit(ActionEvent e) {
        Alert quitAL = new Alert(Alert.AlertType.CONFIRMATION);
        quitAL.setTitle("Quit Hangman!");
        quitAL.setHeaderText(null);
        quitAL.setContentText("Are you sure you want to quit the game?");
        if (quitAL.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }

    }


    public void addLetter(String letter, ArrayList<String> list) {
        list.add(letter);
    }

    public void createSecretWord() {
        StringBuilder updatedSecretWord = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            updatedSecretWord.append("-");
        }

        secretWord = updatedSecretWord.toString();
        resultLB.setText(secretWord);
    }

    public boolean checkIfEntered(String letter, ArrayList<String> enteredLetters) {
        for (String s : enteredLetters) {
            if (s.equals(letter)) {
                return true;
            }
        }
        return false;
    }


    public boolean isCharInWord(String letterGuess, String word) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letterGuess.charAt(0)) return true;
        }
        return false;
    }

    public void updateSecretWord(String letterGuess, String word) {
        StringBuffer tmp = new StringBuffer(secretWord.toString());
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letterGuess.charAt(0)) {
                tmp.setCharAt(i, letterGuess.charAt(0));
            }
        }
        secretWord = tmp.toString();
    }

}
