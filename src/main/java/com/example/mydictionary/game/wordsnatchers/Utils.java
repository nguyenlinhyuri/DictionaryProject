package com.example.mydictionary.game.wordsnatchers;

import com.example.mydictionary.*;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;

public class Utils extends AppUtils {
    public static AnchorPane playAnchorPane;
    public static AnchorPane endAnchorPane;
    public static AnchorPane component;

    public static int word = 0;
    public static int point = 0;

    public static final int time = 15;
    public static int currentTime; // thời gian hiện tại
    public static Timeline timeline;

    public ArrayList<Word> data = new ArrayList<>();
    public ArrayList <Word> randomWord = new ArrayList<>();  // chứa 12 từ được chọn ngẫu nhiên
    public static final String DATA_PATH = "data/dictionaries.txt";

}
