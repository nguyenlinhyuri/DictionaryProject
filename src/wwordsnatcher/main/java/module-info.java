module com.example.wordsnatcher {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wordsnatcher to javafx.fxml;
    exports com.example.wordsnatcher;
}