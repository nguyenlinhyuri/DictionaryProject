package com.example.mydictionary.game.wordbubbles;

import com.example.mydictionary.AppUtils;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.*;
import javafx.util.*;

import java.io.*;
import java.net.URL;
import java.util.*;

public class PlayController extends GameUtils implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button mutedSoundButton;

    @FXML
    private Label wordLabel;

    @FXML
    private Label pointLabel;

    @FXML
    private TextArea enteredWordTextArea;

    @FXML
    private Label prefixLabel;

    @FXML
    private TextField enterdWordTextField;

    @FXML
    private Label timeLabel;

    private final int fromTop = 390;
    private final int fromLeft = 220;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enteredWord.clear();
        numOfWord = 0;
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mediaPlayer != null) mediaPlayer.stop();
        playSound("sound/play.wav", 2);

        timeLeft = time;
        timeline = new Timeline();

        timeline.setCycleCount(Timeline.INDEFINITE); //Timeline sẽ chạy vô thời hạn cho đến khi được dừng lại hoặc bị gián đoạn.
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            timeLeft--;
            if (timeLeft < 0) {
                stop();
                showTimeOut();
                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                delay.setOnFinished(e -> {
                    try {
                        endGame();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                delay.play();

            } else {
                updateTimeLabel();
                if (timeLeft == time * 2 / 3) {
                    prefix = prefix.substring(0, 3);
                    prefixLabel.setText(prefix);
                    showPrefixLabel(prefix, anchorPane, fromTop, fromLeft, 2);
                    updateResultWord();
                } else if (timeLeft == time / 3) {
                    prefix = prefix.substring(0, 2);
                    prefixLabel.setText(prefix);
                    showPrefixLabel(prefix, anchorPane, fromTop, fromLeft, 2);
                    updateResultWord();
                }
            }
        }));

        timeline.play();

        enteredWord.clear();
        randomPrefix();
        updateResultWord();
        enteredWordTextArea.setEditable(false);
        enteredWordTextArea.setWrapText(true);
        showPrefixLabel(prefix, anchorPane, fromTop, fromLeft, 2);
    }

    /**
     * end game
     */
    public void endGame() throws IOException {
        if (mediaPlayer != null) mediaPlayer.stop();
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = AppUtils.class.getResource("game/wordbubbles/view/end.fxml");
        fxmlLoader.setLocation(url);
        try {
            endAnchorPane = fxmlLoader.load();
            AnchorPane.setTopAnchor(endAnchorPane, top1);
            AnchorPane.setLeftAnchor(endAnchorPane, left1);
            playAnchorPane.getChildren().add(endAnchorPane);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * tạm dừng âm thanh
     */
    public void mutedSound(ActionEvent event) {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mutedSoundButton.setText("Unmute Sound");
            mediaPlayer.pause();

        }
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mutedSoundButton.setText("Mute Sound");
            mediaPlayer.play();

        }
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
        System.out.println("read successfully!");
    }

    /**
     * random prefix
     */
    public void randomPrefix() {
        Random random = new Random();
        int randomIdx = random.nextInt(data.size()+1);
        prefix = data.get(randomIdx).substring(0, 4).toLowerCase();
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
        System.out.println("=========================");
        for (String x : result) System.out.println(x);
    }

    public boolean check_start(String prefix, String s) {
        if (s.length() <= 4) return false;
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
            playBubblesSound();
            enteredWord.add(inputWord);
            numOfWord++;
            wordLabel.setText(" Words " + numOfWord);
            if (inputWord.length() == 3 || inputWord.length() == 4) point += 100;
            else if (inputWord.length() == 5) point += 200;
            else if (inputWord.length() == 6) point += 300;
            else if (inputWord.length() == 7) point += 400;
            else if (inputWord.length() == 8) point += 500;
            else if (inputWord.length() == 9) point += 600;
            else if (inputWord.length() >= 10) point += 700;
            pointLabel.setText(" Points " + point);
            if (enteredWord.size() == 1) {
                enteredWordTextArea.setText(inputWord);
            } else {
                enteredWordTextArea.setText(enteredWordTextArea.getText() + "   " + inputWord);
            }
        } else if (!result.contains(inputWord)) {
            showInfomation("Not found this word!", 1);
        } else if (enteredWord.contains(inputWord)) {
            showInfomation("You entered this word!", 1);
        }
        enterdWordTextField.clear();
    }

    /**
     * hiển thị thông báo
     */
    public void showInfomation(String message, int time) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oops!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished(e -> alert.close());
        delay.play();
    }

    /**
     * tạm dừng game
     */
    public void pauseGame(ActionEvent event) throws IOException {
        timeline.pause();
        if (mediaPlayer != null) mediaPlayer.pause();

        try {
            component = FXMLLoader.load(getClass().getResource("view/pause.fxml"));
            AnchorPane.setTopAnchor(component, top1);
            AnchorPane.setLeftAnchor(component, left1);
            playAnchorPane.getChildren().add((Node) component);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * show timeout
     */
    public void showTimeOut() {
        if (timeLeft <= 0) {
            showInfomation("The time has run out!", 2);
        }
    }

    /**
     * hiển thị thông báo prefix trong t giây
     */
    public void showPrefixLabel(String prefix, AnchorPane anchorPane, double top, double left, int time) {
        Label label = new Label(prefix);
        label.setPrefSize(60, 30);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 22px;" +
                "-fx-background-color: #88B6CE;" +
                "-fx-font-family: cambria;" +
                "-fx-border-color: black;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-weight: bold;");

        AnchorPane.setTopAnchor(label, top);
        AnchorPane.setLeftAnchor(label, left);
        anchorPane.getChildren().add(label);

        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished(e -> {
            anchorPane.getChildren().remove(label);
        });
        delay.play();
    }

    /**
     * play sound
     */
    public void playBubblesSound() {
        String[] bubblesSound = {"sound/bubbles_sound.wav",
                "sound/bubbles_sound1.wav",
                "sound/bubbles_sound2.wav",
                "sound/bubbles_sound3.wav"};
        int random = (int) (Math.random() * 4);
        URL resource = getClass().getResource(bubblesSound[random]);
        if (resource != null){
            Media sound = new Media(resource.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);

            // đặt thoi gian phát là 4s
            mediaPlayer.setOnReady(() -> {
                Duration duration = sound.getDuration();
                if (duration.greaterThan(Duration.seconds(4))) {
                    mediaPlayer.setStopTime(Duration.seconds(4));
                }
            });
            mediaPlayer.play();
        }


    }

}
