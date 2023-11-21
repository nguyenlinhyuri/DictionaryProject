package com.example.mydictionary.game.hangman;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML
     ImageView imageUp,imageDown, imageLeft, imageRight;
    @FXML Image right = new Image(getClass().getResourceAsStream("/com/example/mydictionary/game/hangman/image/right.png"));
    @FXML Image up = new Image(getClass().getResourceAsStream("/com/example/mydictionary/game/hangman/image/up.png"));
    @FXML Image down = new Image(getClass().getResourceAsStream("/com/example/mydictionary/game/hangman/image/down.png"));
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
        System.out.println(word);

        commentLB.setText("enter your letter here!");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (mediaPlayer != null) mediaPlayer.stop();
        playSound("sound/music1.mp3", 5);
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

    int countRepeat = 0;
    int countTrue = 0;

    @FXML
    public void submit(ActionEvent event) throws IOException {
        String letterGuess = myTextField.getText(0, 1);
        countTrue = 0;
        if (checkIfEntered(letterGuess, enter)) {
            commentLB.setText("you have already used this letter!");
            countRepeat++;
            Image myImage1 = new Image(getClass().getResourceAsStream("/com/example/mydictionary/game/hangman/image/kho_hieu.gif"));
            if (countRepeat == 1) {
                imageLeft.setImage(myImage1);
                imageDown.setImage(down);
                imageRight.setImage(right);
                imageUp.setImage(up);
            } else if (countRepeat == 2) {

                imageDown.setImage(myImage1);

            } else if (countRepeat ==3 ) {
//                imageCMT.setImage(myImage1);
//                imageCMT2.setImage(myImage1);
                imageRight.setImage(myImage1);
            }
            else if (countRepeat > 3)
            {
                imageUp.setImage(myImage1);
            }

        } else {
            enter.add(letterGuess);
            countRepeat = 0;
            if (isCharInWord(letterGuess, word)) {
                updateSecretWord(letterGuess, word);
                resultLB.setText(secretWord);
                commentLB.setText("nice!!");
                countTrue++;
                Image myImage2 = new Image(getClass().getResourceAsStream("/com/example/mydictionary/game/hangman/image/look.gif"));
                if (countTrue == 1)
                {
                    imageUp.setImage(myImage2);
                    imageLeft.setImage(myImage2);
                    imageRight.setImage(myImage2);
                    imageDown.setImage(myImage2);

                }
            } else {
                hp--;
                Image myImage3 = new Image(getClass().getResourceAsStream("/com/example/mydictionary/game/hangman/image/hehe.gif"));
                if (hp <= 0) {
                    try {
                        component = FXMLLoader.load(getClass().getResource("view/lose.fxml"));
                        AnchorPane.setTopAnchor(component, top1);
                        AnchorPane.setLeftAnchor(component, left1);
                        hangmanAnchorPane.getChildren().add((Node) component);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }
                hpLB.setText("Lives: " + hp);
                commentLB.setText("oh wrong :<  try again !");
                //addLetter(letterGuess, wrong);
                imageUp.setImage(myImage3);
                imageRight.setImage(myImage3);
                imageDown.setImage(myImage3);
                imageLeft.setImage(myImage3);
            }
            if (secretWord.equals(word)) {

                try {
                    component = FXMLLoader.load(getClass().getResource("view/congratulation.fxml"));
                    AnchorPane.setTopAnchor(component, top1);
                    AnchorPane.setLeftAnchor(component, left1);
                    hangmanAnchorPane.getChildren().add((Node) component);
                } catch (IOException e) {
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
