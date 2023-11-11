package com.example.mydictionary.practice;

import com.example.mydictionary.AppUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NotedWord extends AppUtils implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private ListView<String> notedWordListView;

    @FXML
    private TextField searchTextField;


    /**
     * cập nhât danh sách
     */
    public void updateListView() {
        notedWordListView.getItems().addAll(notedWord.keySet());
        notedWordListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String item = notedWordListView.getSelectionModel().getSelectedItem();
                searchTextField.setText(item);
            }
        });

    }

    /**
     * đọc dữ liệu các từ đang học
     */
    public void readDataNotedWord() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(NOTED_WORD_PATH));
        String line;
        while (true){
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e){
                throw new RuntimeException(e);
            }
            String[] parts = line.split("\t");
            if (parts.length>=2){
                notedWord.put(parts[0], parts[1]);
            }
        }
    }

    /**
     * ấn quay lại
     */
    public void backAction(ActionEvent e) {
        rootAnchorPane.getChildren().remove(notedwordAnchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readDataNotedWord();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        updateListView();

    }
}
