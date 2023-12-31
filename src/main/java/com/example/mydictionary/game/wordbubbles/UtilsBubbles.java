package com.example.mydictionary.game.wordbubbles;

import com.example.mydictionary.Game;
import com.example.mydictionary.basic.DictionaryManagement;
import com.example.mydictionary.search.Dictionary;
import javafx.animation.*;
import javafx.scene.layout.AnchorPane;
import java.util.*;

public class UtilsBubbles extends Game {
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
    public static DictionaryManagement dictionaryFunction = new DictionaryManagement();
    public static Dictionary diction = new Dictionary();
    public static final int time = 60;
    public static int timeLeft = time;
    public static Timeline timeline;
}
