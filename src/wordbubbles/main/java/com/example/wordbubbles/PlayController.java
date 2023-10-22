package com.example.wordbubbles;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class PlayController extends GameUtils implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button pauseButton;

    @FXML
    private Label scoreLabel;

    @FXML
    private TextArea enteredWordTextArea;

    @FXML
    private Label prefixLabel;

    @FXML
    private TextField enterdWordTextField;

    @FXML
    private Label timeLabel;

    //    private String prefix = "";
    private ArrayList<String> data = new ArrayList<>(); //  chứa mọi từ tiếng anh
    //    private ArrayList<String> enteredWord = new ArrayList<>(); // chứa nhưng từ đã nhập, sẽ hiển thị lên TextArea
//    private ArrayList<String> result = new ArrayList<>();  // các từ thỏa mãn bắt đầu bằng prefix
    private final String DATA_PATH = "E:\\Java\\intellijJava\\OOPtemp\\WordBubbles\\src\\main\\resources\\com\\example\\wordbubbles\\dictionaries.txt";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enteredWord.clear();
        score = 0;
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        timeline.setCycleCount(Timeline.INDEFINITE); //Timeline sẽ chạy vô thời hạn cho đến khi được dừng lại hoặc bị gián đoạn.
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            timeLeft--;
            if (timeLeft <= 0) {
                stop();
                timeline.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ActionEvent endEvent = (ActionEvent) event;
                        try {
                            endGame(endEvent);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } else updateTimeLabel();
        }));

        timeline.play();

        enteredWord.clear();
        randomPrefix();
        enteredWordTextArea.setEditable(false);
        enteredWordTextArea.setWrapText(true);
        updateResultWord();
    }

    /**
     * end game
     */
    public void endGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("end.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * đọc dữ liệu vào mảng data
     */
    public void readData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(DATA_PATH));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length >= 2) {
                data.add(parts[0]);
            }
        }
    }

    /**
     * random prefix
     */
    public void randomPrefix() {
        Random random = new Random();
        int randomIdx = random.nextInt(data.size());
        prefix = data.get(randomIdx).substring(0, 2).toLowerCase();
        prefixLabel.setText(prefix);
    }

    /**
     * thêm các từ có prefix vào result
     */
    public void updateResultWord() {
        for (String x : data) {
            if (check_start(prefix, x)) {
                result.add(x);
            }
        }
    }

    public boolean check_start(String prefix, String s) {
        if (s.length() <= 2) return false;
        for (int i = 0; i < prefix.length(); i++) {
            if (prefix.charAt(i) != s.charAt(i)) return false;
        }
        return true;
    }

    /**
     * nhập từ vào TextField và hiển thị lên textArea
     */
    @FXML
    public void userEnterWord(ActionEvent event) {
        String inputWord = prefix + enterdWordTextField.getText().toLowerCase().trim();
        if (!enteredWord.contains(inputWord) && result.contains(inputWord)) {
            enteredWord.add(inputWord);
            score++;
            scoreLabel.setText("Score: " + score);
            if (enteredWord.size() == 1) {
                enteredWordTextArea.setText(inputWord);
            } else {
                enteredWordTextArea.setText(enteredWordTextArea.getText() + "   " + inputWord);
            }
        } else if (!result.contains(inputWord)) {
            showInfomation("Not found this word!");
        } else if (enteredWord.contains(inputWord)) {
            showInfomation("You entered this word!");
        }
        enterdWordTextField.clear();
    }

    /**
     * hiển thị thông báo
     */
    public void showInfomation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oops!");
        alert.setHeaderText(null);
        alert.setContentText(message);
//        alert.getDialogPane().getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
        alert.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> alert.close());
        delay.play();
    }

    /**
     * tạm dừng game
     */
    public void pauseGame(ActionEvent event) throws IOException {
        timeline.pause();

        root = FXMLLoader.load(getClass().getResource("pause.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * thời gian
     */
    public void updateTimeLabel() {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        String timeStr = String.format("%02d:%02d", minutes, seconds);
        timeLabel.setText(timeStr);
    }

    /**
     * dừng thời gian
     */
    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

}
