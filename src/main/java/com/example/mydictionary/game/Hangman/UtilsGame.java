package com.example.mydictionary.game.Hangman;

import com.example.mydictionary.AppUtils;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class UtilsGame extends AppUtils {
    public static AnchorPane playAnchorPane;
    public static AnchorPane component;

    public static ArrayList<String> data = new ArrayList<>();
    public static String word;
    public static final String path = "data/hangman_text.txt";

}
