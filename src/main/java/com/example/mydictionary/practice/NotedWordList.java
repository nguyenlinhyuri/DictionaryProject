package com.example.mydictionary.practice;

import com.example.mydictionary.Practice;
import com.example.mydictionary.basic.Word;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.*;

public class NotedWordList extends Practice implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private ListView<String> notedWordListView;

    @FXML
    private TextField searchTextField;

    @FXML
    private TextArea meaningTextArea;

    private ObservableList<String> list = FXCollections.observableArrayList();

    /**
     * tìm từ đã lưu
     */
    public void searchNotedWord() {
        String s = searchTextField.getText();
        if (s != null) {
            s = searchTextField.getText().toLowerCase().trim();
            if (!s.isEmpty()) {
                list.clear();

                for (Map.Entry entry : notedWord.entrySet()) {
                    Word w = new Word((String) entry.getKey(), (String) entry.getValue());
                    if (w.check_start(s)) {
                        list.add(w.getTarget());
                    }
                }
                if (!list.isEmpty()) {
                    notedWordListView.setItems(list);
                } else {
                    list.addAll(notedWord.keySet());
                    notedWordListView.setItems(list);
                }
            } else {
                list.addAll(notedWord.keySet());
                notedWordListView.setItems(list);
            }
        } else {
            updateListView();
        }


    }

    /**
     * thêm từ
     */
    public void addWord(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add a word");
        dialog.setHeaderText(null);
        dialog.setContentText("Meaning");
        dialog.getEditor().setPromptText("Enter your meaning");

        HBox box = new HBox();
        Label target = new Label(" Vocabulary  ");
        TextField targetTextField = new TextField();
        targetTextField.setPromptText("Enter your vocabulary");

        box.getChildren().addAll(target, targetTextField);
        dialog.setGraphic(box);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(item -> {
            if (notedWord.containsKey(targetTextField.getText())) {
                showAlert(Alert.AlertType.INFORMATION, "Oops!", null, "This word has existed in the list.");
            } else {
                notedWord.put(targetTextField.getText(), dialog.getEditor().getText());
                addItemToListView(targetTextField.getText());
            }
        });

        if (!targetTextField.getText().isEmpty() && !dialog.getEditor().getText().isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Add a word", null, "Add successful!");
        }
    }

    /**
     * thêm từ
     */
    public void addItemToListView(String item) {
        list.add(item);
    }

    /**
     * chỉnh sửa từ
     */
    public void editWord(ActionEvent event) {
        String wordToEdit = searchTextField.getText();

        if (wordToEdit != null && !wordToEdit.isEmpty()) {
            System.out.println(wordToEdit);
            meaningTextArea.setEditable(true);
            Button okButton = new Button("OK");
            okButton.setStyle("-fx-font-family: Cambria;" +
                    "-fx-background-color:  #007196;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");

            AnchorPane.setTopAnchor(okButton, 412.0);
            AnchorPane.setLeftAnchor(okButton, 740.0);
            notedwordAnchorPane.getChildren().add(okButton);

            okButton.setOnAction(e -> {
                notedWord.remove(wordToEdit);
                notedWord.put(wordToEdit, meaningTextArea.getText());
                showAlert(Alert.AlertType.INFORMATION, "Information", null, "Edit successfully!");
                meaningTextArea.setEditable(false);
                notedwordAnchorPane.getChildren().remove(okButton);
            });
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Empty Input", "Please enter a word to edit.");
        }
    }

    /**
     * xóa từ
     */
    public void deleteWord(ActionEvent event) {
        String wordToDelete = searchTextField.getText();

        if (wordToDelete != null) {
            if (!notedWord.containsKey(wordToDelete)) {
                showAlert(Alert.AlertType.WARNING, "Not Found", null, "Not found this word");
                return;
            }
            notedWord.remove(searchTextField.getText(), meaningTextArea.getText());
            ObservableList<String> item = notedWordListView.getItems();
            item.remove(wordToDelete);
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Empty Input", "Please enter a word to delete.");
        }
    }

    public void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * cập nhât danh sách
     */
    public void updateListView() {
        list.addAll(notedWord.keySet());
        notedWordListView.setItems(list);

        notedWordListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String item = notedWordListView.getSelectionModel().getSelectedItem();
                searchTextField.setText(item);
                meaningTextArea.setText(notedWord.get(item));
            }
        });
        for (Map.Entry entry : notedWord.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue());
        }
    }

    /**
     * ấn quay lại
     */
    public void backAction(ActionEvent e) {
        practiceAnchorPane.getChildren().remove(notedwordAnchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        meaningTextArea.setWrapText(true);
        meaningTextArea.setEditable(false);
        updateListView();
        searchTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                searchNotedWord();
            }
        });
    }
}
