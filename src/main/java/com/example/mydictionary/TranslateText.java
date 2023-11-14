package com.example.mydictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javazoom.jl.player.Player;
import org.apache.commons.text.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;


public class TranslateText implements Initializable {

    @FXML
    private Label input;
    @FXML
    private Label output;

    @FXML
    private TextArea textInput;

    @FXML
    private TextArea view;

    public static String text;


    /**
     * khoi dong
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text = new String();
        textInput.setWrapText(true);
        view.setWrapText(true);
        input.setText("English");
        output.setText("Vietnamese");

    }
    @FXML
    public void changeLanguage(ActionEvent e)
    {
        String languageInput = input.getText();
        if ( languageInput.equals("Vietnamese"))
        {
            input.setText("English");
            output.setText("Vietnamese");
            textInput.clear();
            view.clear();
        }
        else
        {
            output.setText("English");
            input.setText("Vietnamese");
            textInput.clear();
            view.clear();
        }
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
        String input= textInput.getText();
        if (language.equals("Vietnamese")) {
            playsoundVi(input);
        } else if (language.equals("English")) {
            playsoundEn(input);
        }
    }

    /**
     * đọc output.
     */

    @FXML
    public void playSoundOutput(ActionEvent e) {
        String language = output.getText();
        String output = view.getText();
        if (language.equals("Vietnamese")) {
            playsoundEn(output);
        } else if (language.equals("English")) {
            playsoundVi(output);
        }
    }

    /**
     * dịch sang tếng anh.
     */
    public static String translateToEn(String text) {
        try {
            return translate("vi", "en", text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }

    /**
     * dich sang tieng viet.
     */
    public static String translateToVi(String text) {
        try {
            return translate("en", "vi", text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }



    private static String translate(String langFrom, String langTo, String text) throws IOException {
        String APIKEY = "AKfycby3AOWmhe32TgV9nW-Q0TyGOEyCHQeFiIn7hRgy5m8jHPaXDl2GdToyW_3Ys5MTbK6wjg";
        URL url = new URL("https://script.google.com/macros/s/" + APIKEY + "/exec?q="
                + URLEncoder.encode(text, StandardCharsets.UTF_8)
                + "&target=" + langTo + "&source=" + langFrom);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();

        request.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));

        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = inputStream.readLine()) != null) {
            response.append(inputLine);
        }
        inputStream.close();
        return StringEscapeUtils.unescapeHtml4(response.toString());
    }

    public static void playsoundVi(String text) {
        Sound("vi", text);
    }

    public static void playsoundEn(String text) {
        Sound("en", text);
    }

    public static void Sound(String language, String text) {
        try {
            String api =
                    "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                            + language
                            + "&client=tw-ob&q="
                            + URLEncoder.encode(text, StandardCharsets.UTF_8);
            URL url = new URL(api);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream audio = con.getInputStream();
            new Player(audio).play();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getting voices");
        }
    }


}