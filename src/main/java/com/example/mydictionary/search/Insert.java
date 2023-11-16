package com.example.mydictionary.search;

import com.example.mydictionary.AppUtils;
import com.example.mydictionary.jdbc.JdbcDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class Insert extends AppUtils {
    @FXML
    private TextField vocabTextField;

    @FXML
    private TextField meaningTextField;

    @FXML
    private Button insertButton;

    @FXML
    public void insertAction(ActionEvent event) throws SQLException {
        String vocab = vocabTextField.getText();
        String meaning = meaningTextField.getText();

        JdbcDao jdbcDao = new JdbcDao();
        jdbcDao.addWordToDatabase(vocab, meaning);

        vocabTextField.clear();
        meaningTextField.clear();
    }
}
