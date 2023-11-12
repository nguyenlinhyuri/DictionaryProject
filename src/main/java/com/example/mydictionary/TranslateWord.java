package com.example.mydictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apache.commons.text.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class TranslateWord implements Initializable {
    @FXML
    private Button X;

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Button edit;

    @FXML
    private Button ggTranslate;

    @FXML
    private TextField inputWord;

    @FXML
    private ListView<String> listWord;

    @FXML
    private Button pronounceBT;

    @FXML
    private TextArea viewTaget;
    @FXML
    private ChoiceBox<String> choiceLanguage;
    private String[] language = {"Vietnamese", "English"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceLanguage.getItems().addAll(language);
    }


    @FXML
    public void Trans(ActionEvent e) {
        String input = inputWord.getText();
        String lan = choiceLanguage.getValue();
        if (lan.equals(language[0])) {
            viewTaget.setText(translateToEn(input));
        } else if (lan.equals(language[1])) {
            viewTaget.setText(translateToVi(input));
        }

    }

    public static String translateToEn(String text) {
        try {
            return translate("vi", "en", text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }

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


}
