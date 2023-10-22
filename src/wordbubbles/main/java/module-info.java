module com.example.wordbubbles {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
                            
    opens com.example.wordbubbles to javafx.fxml;
    exports com.example.wordbubbles;
}