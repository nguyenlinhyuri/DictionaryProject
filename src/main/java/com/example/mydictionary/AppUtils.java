package com.example.mydictionary;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppUtils {
    public static Stage stage;
    public static Scene scene;
    public static Parent root;
    public static AnchorPane rootAnchorPane;

    /**
     * game
     */
    public static boolean isGameScene = false;
    public static AnchorPane gameAnchorPane;
    public static AnchorPane bubblesAnchorPane;
    public static AnchorPane snatchersAnchorPane;
    public static AnchorPane hangmanAnchorPane;

    /**
     * practice
     */
    public static boolean isPracticeScene = false;
    public static AnchorPane practiceAnchorPane;
    public static AnchorPane flashcardAnchorPane;
    public static AnchorPane notedwordAnchorPane;
    public static AnchorPane startexercisesAnchorPane;
    public static AnchorPane endexercisesAnchorPane;
    public static AnchorPane exercisesAnchorPane;

    /**
     * search
     */
    public static boolean isSearchScene = false;
    public static AnchorPane searchAnchorPane;

    /**
     * translate text
     */
    public static boolean isTranslateScene = false;
    public static AnchorPane translateAnchorPane;

    public static Media media;
    public static MediaPlayer mediaPlayer;

    public static String USER_NAME = "";

    public static final double top = 118.0;
    public static final double left = 71.0;
    public static final double top1 = 0.0;
    public static final double left1 = 0.0;

    public static Map<String, String> notedWord = new HashMap<>();  // từ đã lưu
    public static final String NOTED_WORD_PATH = "E:\\Java\\intellijJava\\OOPtemp\\MyDictionary\\src\\main\\resources\\com\\example\\mydictionary\\practice\\data.txt";



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
    public void showNewScene(AnchorPane root, AnchorPane newScene, String fxml_path, double top, double left) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource(fxml_path);
        fxmlLoader.setLocation(url);
        try {
            newScene = fxmlLoader.load();
            AnchorPane.setTopAnchor(newScene, top);
            AnchorPane.setLeftAnchor(newScene, left);
            root.getChildren().add(newScene);
        } catch (IOException e){
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

    /**
     * load âm thanh cho app
     */
    public void playSound(String path, int times){
        URL resource = getClass().getResource(path);
        if (resource != null) {
            media = new Media(resource.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(times);
            mediaPlayer.play();
        }
    }


}
