package com.example.mydictionary;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AppUtils {
    public static Stage stage;
    public static Scene scene;
    public static Parent root;
    public static AnchorPane rootAnchorPane;  //110.0 - 8.0
    public static AnchorPane gameAnchorPane;
    public static AnchorPane practiceAnchorPane;

    public static String USER_NAME = "";

    public static final double top = 118.0;
    public static final double left = 71.0;



    /**
     * show Alert information
     */
    public void showAlertInformation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * hiển thị 1 anchorpane mới lên 1 anchorpane gốc (giữ nguyên các nội dung cũ) tại vị trí top, left
     */
    public void showNewScene(AnchorPane root, String fxml_path, double top, double left) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource(fxml_path);
        fxmlLoader.setLocation(url);
        try {
            AnchorPane newScene = fxmlLoader.load();
            AnchorPane.setTopAnchor(newScene, top);
            AnchorPane.setLeftAnchor(newScene, left);
            root.getChildren().add(newScene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * dừng chương trình trong t giây
     */
    public void delayProgram(int t) {
        try {
            Thread.sleep(t * 1000); // 2000 milliseconds = 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * mouse click
     */
    public void mouseClick() {
        Media media = new Media(getClass().getResource("sound/mouse_click.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());
    }


}
