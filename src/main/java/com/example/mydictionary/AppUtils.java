package com.example.mydictionary;

import com.example.mydictionary.basic.Word;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.*;
import javafx.stage.Stage;

import java.io.*;
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

    /**
     * practice
     */
    public static boolean isPracticeScene = false;
    public static AnchorPane practiceAnchorPane;

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
    public static int numOfWordsPracticed = 0;  // số từ đã luyện ập

    public static final double top = 118.0;
    public static final double left = 71.0;
    public static final double top1 = 0.0;
    public static final double left1 = 0.0;

    public static Map<String, String> notedWord = new HashMap<>();  // từ đã lưu
    public static final String NOTED_WORD_PATH = "data/notedwords.txt";

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

    /**
     * đọc dữ liệu các từ đã đánh dấu
     */
    public void readNotedWordData() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(NOTED_WORD_PATH));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length >= 2){
                notedWord.put(parts[0], parts[1]);
            }
        }
        System.out.println("read notedwords successful!");
    }

    /**
     * ghi dữ liệu ra file khi thoát chương trình
     */
    public void writeNotedWordData() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(NOTED_WORD_PATH));
        for (Map.Entry entry : notedWord.entrySet()){
            String line = entry.getKey() + "\t" + entry.getValue();
            bw.write(line);
            bw.newLine();
        }
        System.out.println("write notedwords successful!");
    }
}
