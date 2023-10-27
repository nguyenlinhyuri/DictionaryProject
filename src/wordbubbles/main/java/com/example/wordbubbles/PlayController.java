package com.example.wordbubbles;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;

public class PlayController extends GameUtils implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button pauseButton;

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

//    private String prefix = "";
//    private ArrayList<String> data = new ArrayList<>(); //  chứa mọi từ tiếng anh
//    private ArrayList<String> enteredWord = new ArrayList<>();
//    private ArrayList<String> result = new ArrayList<>();
//    private final String DATA_PATH = "E:\\Java\\intellijJava\\OOPtemp\\WordBubbles\\src\\main\\resources\\com\\example\\wordbubbles\\dictionaries.txt";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enteredWord.clear();
        numOfWord = 0;
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        timeLeft = time;
        timeline = new Timeline();

        timeline.setCycleCount(Timeline.INDEFINITE); //Timeline sẽ chạy vô thời hạn cho đến khi được dừng lại hoặc bị gián đoạn.
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            timeLeft--;
            if (timeLeft < 0){
                stop();
                showTimeOut();
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(e -> {
                    try {
                        endGame();
                    } catch (IOException ex){
                        ex.printStackTrace();
                    }
                });
                delay.play();

            } else{
                updateTimeLabel();
                if (timeLeft == time*2/3){
                    prefix = prefix.substring(0, 3);
                    prefixLabel.setText(prefix);
                    updateResultWord();
                } else if (timeLeft == time/3){
                    prefix = prefix.substring(0, 2);
                    prefixLabel.setText(prefix);
                    updateResultWord();
                }
            }
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
    public void endGame() throws IOException {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource("end.fxml"));
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add((Node) component);
        } catch (IOException e) {
            e.printStackTrace();
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
    }

    /**
     * random prefix
     */
    public void randomPrefix() {
        Random random = new Random();
        int randomIdx = random.nextInt(data.size());
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
            enteredWord.add(inputWord);
            numOfWord++;
            wordLabel.setText("Word: " + numOfWord);
            pointLabel.setText("Point: " + point);
            if (inputWord.length() == 3 || inputWord.length() == 4) point += 100;
            else if (inputWord.length() == 5) point += 200;
            else if (inputWord.length() == 6) point += 300;
            else if (inputWord.length() == 7) point += 400;
            else if (inputWord.length() == 8) point += 500;
            else if (inputWord.length() == 9) point += 600;
            else if (inputWord.length() >= 10) point += 700;
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
//        gameState.setPausePrefix(prefix);
//        gameState.setPauseScore(score);
//        gameState.setPauseEnteredWord(enteredWord);
//        gameState.setPauseData(data);
//        gameState.setPauseEnteredWordTextArea(enteredWordTextArea.getText());
//        gameState.setPauseResult(result);
//        gameState.setPauseTimeLeft(timeLeft);

        stop();

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

    /**
     * show timeout
     */
    public void showTimeOut(){
        if (timeLeft <= 0){
            showInfomation("The time has run out!", 1);
        }
    }
}
