package com.example.wordbubbles;

import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameUtils {
    public static Parent root;
    public static Scene scene;
    public static Stage stage;

    public static int numOfWord = 0;
    public static int point = 0;
    public static String prefix = "";
    public static ArrayList<String> data = new ArrayList<>(); //  chứa mọi từ tiếng anh
    public static ArrayList<String> enteredWord = new ArrayList<>();
    public static ArrayList<String> result = new ArrayList<>();
    public static final String DATA_PATH = "E:\\Java\\intellijJava\\OOPtemp\\WordBubbles\\src\\main\\resources\\com\\example\\wordbubbles\\dictionaries.txt";


    public static int time = 60;
    public static int timeLeft = time;
    public static Timeline timeline;

}
