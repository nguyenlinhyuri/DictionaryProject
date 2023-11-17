package com.example.mydictionary.translatetext;


import com.example.mydictionary.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javazoom.jl.player.Player;
import org.apache.commons.text.StringEscapeUtils;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import static com.example.mydictionary.api.SpeechToTextAPI.SoundEn;
import static com.example.mydictionary.api.SpeechToTextAPI.playsoundVi;
import static com.example.mydictionary.api.googleAPI.translateToEn;
import static com.example.mydictionary.api.googleAPI.translateToVi;


public class TranslateText extends AppUtils implements Initializable {

    @FXML
    private Label input;
    @FXML
    private Label output;

    @FXML
    private TextArea textInput;

    @FXML
    private TextArea view;

    @FXML
    private Button backButton;

    public static String text;


    /**
     * back
     */
    public void backAction(ActionEvent event) {
        rootAnchorPane.getChildren().remove(translateAnchorPane);
        isTranslateScene = false;
    }

    /**
     * khoi dong
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text = new String();
        textInput.setWrapText(true);
        view.setWrapText(true);
        view.setEditable(false);
        input.setText("English");
        output.setText("Vietnamese");

    }

    /**
     * khi clck vào nút chuyển đổi ngôn ngữ
     */

    @FXML
    public void changeLanguage(ActionEvent event) {
        String language = input.getText();
        if (language.equals("English")) {
            output.setText("English");
            input.setText("Vietnamese");

        } else {
            input.setText("English");
            output.setText("Vietnamese");
        }
        textInput.clear();
        view.clear();
    }

    /**
     * khi an nut enter thi thuc hien dich.
     */
    @FXML
    public void Trans(ActionEvent e) {
        text = textInput.getText();
        String language = input.getText();
        if (language.equals("Vietnamese")) {
            view.setText(translateToEn(text));

        } else if (language.equals("English")) {
            view.setText(translateToVi(text));
        }
    }

    /**
     * đọc đầu vào.
     */

    @FXML
    public void playSoundInput(ActionEvent e) {
        String language = input.getText();
        String input = textInput.getText();
        if (language.equals("Vietnamese")) {
            playsoundVi(input);
        } else {
            SoundEn(input);
        }
    }

    /**
     * đọc output.
     */
    @FXML
    public void playSoundOutput(ActionEvent e) {
        String language = output.getText();
        String output = view.getText();
        if (language.equals("English")) {
            SoundEn(output);
        } else if (language.equals("Vietnamese")) {
            playsoundVi(output);
        }
    }


}