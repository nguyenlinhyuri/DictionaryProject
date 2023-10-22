package com.example.wordbubbles;

import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameUtils {
    public Parent root;
    public Scene scene;
    public Stage stage = new Stage();

    public int score = 0;
    public String prefix = "";

    public ArrayList<String> enteredWord = new ArrayList<>();
    public ArrayList<String> result = new ArrayList<>();

    public int timeLeft = 5;
    public Timeline timeline = new Timeline();

}
