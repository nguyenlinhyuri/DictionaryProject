package com.example.mydictionary.game.wordbubbles;

import com.example.mydictionary.Game;
import javafx.animation.*;
import javafx.scene.layout.AnchorPane;
import java.util.*;

public class GameUtils extends Game {
    public static AnchorPane playAnchorPane;
    public static AnchorPane endAnchorPane;
    public static AnchorPane component;

    public static int numOfWord = 0;
    public static int point = 0;
    public static String prefix = "";
    public static ArrayList<String> data = new ArrayList<>(); //  chứa mọi từ tiếng anh
    public static ArrayList<String> enteredWord = new ArrayList<>();
    public static ArrayList<String> result = new ArrayList<>();
    public static final String DATA_PATH = "data/dictionaries.txt";

    public static final int time = 60;
    public static int timeLeft = time;
    public static Timeline timeline;
}
