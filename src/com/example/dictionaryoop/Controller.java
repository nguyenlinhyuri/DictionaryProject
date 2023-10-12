package com.example.dictionaryoop;

import commandline.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;


public class Controller implements Initializable {
    @FXML
    private TextField searchTextField;

    @FXML
    private ListView wordListView;

    @FXML
    private Label wordLabel;

    @FXML
    private TextArea meaningTextArea;

    private static final String DATA_PATH = "E:\\Java\\intellijJava\\DictionaryOOP\\src\\main\\resources\\com\\example\\dictionaryoop\\data.txt";
    private Map<String, Word> data = new TreeMap<>();
    private ObservableList<String> list = FXCollections.observableArrayList();


    /**
     * đọc file dictionaries vào Map data
     */
    public void readData() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(DATA_PATH));
        String englishWord = br.readLine();
        englishWord = englishWord.replace("|", "");
        String line;
        while ((line = br.readLine()) != null){
            englishWord = englishWord.trim();
            String vietnameseWord = line + "\n";
            while ((line = br.readLine()) != null){
                if (!line.startsWith("|")) vietnameseWord += line + "\n";
                else {
                    englishWord = line.replace("|", "");
                    break;
                }
            }
            Word word = new Word(englishWord, vietnameseWord);
            data.put(englishWord, word);
        }
    }

    /**
     * khi nhập từ vào searchTextField
     */
    @FXML
    private void searchWord(){
        list.clear();
        String searchKey = searchTextField.getText().toLowerCase().trim();
        if (searchKey.isEmpty()) list.clear();

        for (Word x : data.values()){
            if (x.check_start(searchKey)){
                list.add(x.getWord_target());
            }
        }
        if (list.isEmpty()){
            wordListView.setVisible(false);
        } else wordListView.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                searchWord();
            }
        });

        // khi click 1 từ trong listview
        wordListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                String word = (String) wordListView.getSelectionModel().getSelectedItem();
                wordLabel.setText("  " + word);
                meaningTextArea.setText(data.get(word).getWord_explain());
            }
        });

        meaningTextArea.setEditable(false);
    }
}