package com.example.mydictionary.practice;

import com.example.mydictionary.AppUtils;
import com.example.mydictionary.jdbc.JdbcDao;
import javafx.beans.value.*;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.*;

public class NotedWordList extends AppUtils implements Initializable {
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

    private JdbcDao jdbcDao = new JdbcDao();

    /**
     * thêm từ
     */
    public void addWord(ActionEvent event) {

    }

    /**
     * chỉnh sửa từ
     */
    public void editWord(ActionEvent event) {


    }

    /**
     * xóa từ
     */
    public void deleteWord(ActionEvent event) {
        String wordToDelete = searchTextField.getText();

        if (wordToDelete.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Empty Input", "Please enter a word to delete.");
            return;
        }

        try {
            jdbcDao.deleteWordFromDatabase(wordToDelete);
            ObservableList<String> item = notedWordListView.getItems();
            item.remove(wordToDelete);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Database Error", "An error occurred while accessing the database.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * cập nhât danh sách
     */
    public void updateListView() throws SQLException {
        List<String> vocabList = jdbcDao.getAllWords();
        notedWordListView.getItems().addAll(vocabList);

        notedWordListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String item = notedWordListView.getSelectionModel().getSelectedItem();
                searchTextField.setText(item);
                String meaning = null;

                try {
                    meaning = jdbcDao.getMeaning(item);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (meaning != null) meaningTextArea.setText(meaning);
            }
        });

    }

    /**
     * đọc dữ liệu các từ đang học
     */
//    public void readDataNotedWord() throws FileNotFoundException {
//        BufferedReader br = new BufferedReader(new FileReader(NOTED_WORD_PATH));
//        String line;
//        while (true) {
//            try {
//                if ((line = br.readLine()) == null) break;
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            String[] parts = line.split("\t");
//            if (parts.length >= 2) {
//                notedWord.put(parts[0], parts[1]);
//            }
//        }
//    }

    /**
     * ấn quay lại
     */
    public void backAction(ActionEvent e) {
        practiceAnchorPane.getChildren().remove(notedwordAnchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
//            readDataNotedWord();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        meaningTextArea.setWrapText(true);
        meaningTextArea.setEditable(false);
        try {
            updateListView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
