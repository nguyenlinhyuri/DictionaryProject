package com.example.mydictionary.game.wordsnatchers;

import com.example.mydictionary.*;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ArrayList;

public class Utils extends AppUtils {
    public static AnchorPane component;

    public static int word = 0;
    public static int point = 0;

    public static final int time = 15;
    public static int currentTime; // thời gian hiện tại
    public static Timeline timeline;

    public static Media media;
    public static MediaPlayer mediaPlayer;

    public ArrayList<Word> data = new ArrayList<>();
    public ArrayList <Word> randomWord = new ArrayList<>();  // chứa 12 từ được chọn ngẫu nhiên
    public static final String DATA_PATH = "E:\\Java\\intellijJava\\OOPtemp\\MyDictionary\\src\\main\\resources\\com\\example\\mydictionary\\data\\dictionaries.txt";

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
