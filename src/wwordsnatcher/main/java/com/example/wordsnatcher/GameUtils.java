package com.example.wordsnatcher;

import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

class Word{
    private String target;
    private String explain;

    public Word(String target, String explain) {
        this.target = target;
        this.explain = explain;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}

public class GameUtils {
    public static Parent root;
    public static Scene scene;
    public static Stage stage = new Stage();

    public static int word = 0;
    public static int point = 0;

    public static int time = 10;
    public static int currentTime; // thời gian hiện tại
    public static Timeline timeline;

    public ArrayList <Word> data = new ArrayList<>();
    public ArrayList <Word> randomWord = new ArrayList<>();  // chứa 12 từ được chọn ngẫu nhiên
    public String DATA_PATH = "E:\\Java\\intellijJava\\OOPtemp\\WordSnatcher\\src\\main\\resources\\com\\example\\wordsnatcher\\dictionaries.txt";
}
