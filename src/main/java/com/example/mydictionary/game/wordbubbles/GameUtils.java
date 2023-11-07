package com.example.mydictionary.game.wordbubbles;

import com.example.mydictionary.AppUtils;
import javafx.animation.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.*;

import java.net.URL;
import java.util.*;

public class GameUtils extends AppUtils {
    public static AnchorPane component;

    public static Media media;
    public static MediaPlayer mediaPlayer;

    public static int numOfWord = 0;
    public static int point = 0;
    public static String prefix = "";
    public static ArrayList<String> data = new ArrayList<>(); //  chứa mọi từ tiếng anh
    public static ArrayList<String> enteredWord = new ArrayList<>();
    public static ArrayList<String> result = new ArrayList<>();
    public static final String DATA_PATH = "game/wordbubbles/dictionaries.txt";


    public static int time = 60;
    public static int timeLeft = time;
    public static Timeline timeline;

    public void playSound(String path, int times){
        URL resource = getClass().getResource(path);
        System.out.println(resource);
        if (resource != null) {
            media = new Media(resource.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(times);
            mediaPlayer.play();
        }
    }
}
