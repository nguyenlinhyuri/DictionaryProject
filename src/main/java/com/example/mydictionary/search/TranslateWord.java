package com.example.mydictionary.search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.mydictionary.api.SpeechToTextAPI.SoundEn;


public class TranslateWord implements Initializable {

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
    private WebView viewTaget;

    private static final String DATA_FILE_PATH = "data/E_V.txt";

    private Dictionary diction = new Dictionary();
    private dictionaryFunction dictionaryFunction = new dictionaryFunction();
    // list dùng cho listView
    ObservableList<String> list = FXCollections.observableArrayList();
    private int indexOfSelectedWord; // thứ tự của từ được chọn

    private int firstIndexOfListFound = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // đọc dữ liệu từ file txt
        dictionaryFunction.readFromFile(diction , DATA_FILE_PATH);
//        System.out.println(diction.size());
        // dẩy dữ liệu vào trie
        dictionaryFunction.setTrie(diction);
        setListDefault(0);
        inputWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (inputWord.getText().isEmpty()) {
                    setListDefault(0);
                }
                else
                {
                    handleInputText();
                }
            }
        });


    }
    /**
     * khi gõ tìm kiếm từ trong textFied
     */
    @FXML
    private void handleInputText()
    {
        list.clear();
        String input = inputWord.getText().trim();
        list = dictionaryFunction.lookUp(diction , input);
        if (list.isEmpty())
        {
            setListDefault(firstIndexOfListFound);
        }
        else {
            listWord.setItems(list);
            firstIndexOfListFound = dictionaryFunction.searchWord(diction , list.get(0));
        }
    }
    /**
     * khi click chonj 1 tu trong list view
     */
    @FXML
    private void handleMouseClickaWord( MouseEvent arg0 ) {

        String selectedWord = listWord.getSelectionModel().getSelectedItem();  //  từ đã click
        if (selectedWord != null) {
            indexOfSelectedWord = dictionaryFunction.searchWord(diction , selectedWord);// lấy chỉ số của từ đó trong từ điển
            inputWord.setText(selectedWord);
            if (indexOfSelectedWord == -1) { // nếu không thấy
//                System.out.println("fail");
                return;
            }


            viewTaget.getEngine().loadContent((diction.get(indexOfSelectedWord).getExplain()),"text/html");

        }
    }
    @FXML
    public void sumit(ActionEvent e)
    {
        String input = inputWord.getText();
        int index = dictionaryFunction.searchWord(diction,input);
        viewTaget.getEngine().loadContent(diction.get(index).getExplain(),"text/html");
    }
    @FXML
    public void pronounce(ActionEvent e)
    {
        String text = inputWord.getText();
        SoundEn(text);
    }

    /**
     * tạo 1 danh sách cho list view
     * @param ind (index) vị trí.
     */
    private void setListDefault(int ind)
    {
        list.clear();
        for (int i = ind ; i < ind + 15 ; i++)
        {
            list.add(diction.get(i).getWord());
        }
        listWord.setItems(list);
        viewTaget.getEngine().loadContent(diction.get(ind).getExplain(), "text/html");

    }





}
