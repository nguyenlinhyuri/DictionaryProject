module com.example.mydictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires java.sql;
    requires org.apache.commons.text;
    requires jlayer;

    opens com.example.mydictionary to javafx.fxml;
    opens com.example.mydictionary.practice to javafx.fxml;
    opens com.example.mydictionary.search to javafx.fxml;
    opens com.example.mydictionary.game.wordbubbles to javafx.fxml;
    opens com.example.mydictionary.game.wordbubbles.view to javafx.fxml;
    opens com.example.mydictionary.game.wordsnatchers to javafx.fxml;
    opens com.example.mydictionary.game.wordsnatchers.view to javafx.fxml;
    opens com.example.mydictionary.game.hangman to javafx.fxml;
    opens com.example.mydictionary.game.hangman.view to javafx.fxml;
    opens com.example.mydictionary.translatetext to javafx.fxml;

    exports com.example.mydictionary;
    exports com.example.mydictionary.practice;
    exports com.example.mydictionary.search;
    exports com.example.mydictionary.game.wordbubbles;
    exports com.example.mydictionary.game.wordsnatchers;
    exports com.example.mydictionary.game.hangman;
    exports com.example.mydictionary.translatetext;
}