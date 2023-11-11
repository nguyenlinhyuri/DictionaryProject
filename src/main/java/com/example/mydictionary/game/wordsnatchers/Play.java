package com.example.mydictionary.game.wordsnatchers;

import com.example.mydictionary.Word;
import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Play extends Utils implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button pauseButton;

    @FXML
    private ProgressBar timeProgressBar;

    @FXML
    private TextArea explainTextArea;

    @FXML
    private Label pointLabel;

    @FXML
    private Label wordLabel;

    @FXML
    private Button hintButton;

    @FXML
    private Button mutedSoundButton;

    @FXML
    private FlowPane flowPaneResult;


    private GridPane gridPaneButton = new GridPane();

    private int currentWordIndex;  // chỉ số của từ hiện tại
    private Word currentWord;      // từ hiện tại
    private boolean isHinted = false;


    /**
     * đọc dữ liệu
     */
    public void readData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(DATA_PATH));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length >= 2) {
                data.add(new Word(parts[0], parts[1]));
            }
        }
        System.out.println("read successfully");
    }

    /**
     * lấy ngẫu nhiên 10 từ
     * cập nhật mảng randomWord
     */
    public void randomWord() {
        Random random = new Random();
        int cnt = 0;
        while (cnt < 12) {
            int randomIdx = random.nextInt(data.size());
            Word w = data.get(randomIdx);
            if (!randomWord.contains(w)
                    && w.getTarget().length() >= 3
                    && w.getTarget().length() <= 7) {
                randomWord.add(w);
                cnt++;
            }
        }
        Collections.shuffle(randomWord);  // trộn danh sách

        for (Word x : randomWord) {
            System.out.println(x.getTarget() + " | " + x.getExplain());
        }
    }


    /**
     * pause game
     */
    @FXML
    public void pauseGame(ActionEvent event) throws IOException {
        timeline.pause();

        try{
            component = FXMLLoader.load(getClass().getResource("view/pause.fxml"));
            AnchorPane.setTopAnchor(component, top);
            AnchorPane.setLeftAnchor(component, left);
            rootAnchorPane.getChildren().add((Node) component);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * run game
     */
    public void runGame() {
        double currentProgress = 1.0;
        currentTime = time;
        showWord();
        timeline.play();
    }

    /**
     * cập nhật thời gian qua mỗi từ
     */
    public void updateProgressbar() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
        timeline = new Timeline();
        timeProgressBar.setProgress(1.0);
        currentTime = time;
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            currentTime--;
            if (currentTime <= 0) {
                timeline.stop();
                handleTimeout();
            } else {
                double progress = (double) currentTime / time;
                timeProgressBar.setProgress(progress);
            }
        }));
        timeline.play();
    }

    /**
     * kiểm tra các nút có xếp thành từ đúng hay không
     */
    public boolean checkWord() {
        if (gridPaneButton.getChildren().isEmpty()) {
            String strFromFlowPane = "";
            for (Node node : flowPaneResult.getChildren()) {
                Button btn = (Button) node;
                strFromFlowPane += btn.getText();
            }
            if (strFromFlowPane.equals(currentWord.getTarget())) {
                return true;
            }
        }
        return false;
    }

    /**
     * chuyển sang từ tiếp theo
     */
    public void nextWord() {
        isHinted = false;
        // kiểm tra đã duyệt hết mảng ramdomWord hay chưa
        if (currentWordIndex < randomWord.size() - 1) {
            // vẫn còn từ tiếp theo
            currentWordIndex++;
            showWord();
        } else {
            // khi đã hoàn thành tất cả các từ
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(e -> {
                // tạm dừng 1 giây -> chuyển tới giao diện kết quả
                try {
                    mediaPlayer.stop();  // dừng nhạc
                    resultScreen();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            delay.play();
        }
    }

    /**
     * hiện đáp án nếu từ đó sắp xếp sai
     */
    public void showAnswer() {
        flowPaneResult.getChildren().clear();
        gridPaneButton.getChildren().clear();

        char[] c = currentWord.getTarget().toCharArray();
        for (char x : c) {
            Button btn = new Button(String.valueOf(x));
            btn.setPrefSize(55, 55);
            btn.setStyle("-fx-text-fill: black;" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-family: cambria;" +
                    "-fx-background-radius: 20;" +
                    "-fx-background-color: white;");
            flowPaneResult.getChildren().add(btn);
        }
    }

    /**
     * hiển thị từ hiện tại vào các nút chứa ký tự
     */
    public void showWord() {
        updateProgressbar();

        currentWord = randomWord.get(currentWordIndex);
        explainTextArea.setText(currentWord.getExplain());

        flowPaneResult.getChildren().clear();
        gridPaneButton.getChildren().clear();

        String target = currentWord.getTarget();
        ArrayList<Character> c = new ArrayList<>();
        for (int i = 0; i < target.length(); i++) c.add(target.charAt(i));

        int numRow, numCol;

        if (c.size() <= 6) {
            numRow = 1;
            numCol = c.size();
        } else {
            numRow = 2;
            numCol = 6;
        }
        // set kích thước cho gridPane
        for (int i = 0; i < numRow; i++) {
            RowConstraints row = new RowConstraints();
            gridPaneButton.getRowConstraints().add(row);
        }
        for (int j = 0; j < numCol; j++) {
            ColumnConstraints column = new ColumnConstraints();
            gridPaneButton.getColumnConstraints().add(column);
        }
        int i = 0; // chỉ số cột
        int j = 0; // chỉ số hàng

        Collections.shuffle(c);  // xáo trộn các chữ cái

        for (char x : c) {
            Button btn = new Button(String.valueOf(x));
            btn.setStyle("-fx-text-fill: black;" +
                    "-fx-font-size: 20px;" +
                    "-fx-font-family: cambria;" +
                    "-fx-background-radius: 20;" +
                    "-fx-background-color: white;");
            btn.setPrefSize(55, 55);
            btn.setOnAction(event -> {
                if (flowPaneResult.getChildren().contains(btn)) {   // tính năng delete
                    flowPaneResult.getChildren().remove(btn);
                    gridPaneButton.getChildren().add(btn);
                } else if (gridPaneButton.getChildren().contains(btn)) {
                    gridPaneButton.getChildren().remove(btn);
                    flowPaneResult.getChildren().add(btn);
                }

                if (gridPaneButton.getChildren().isEmpty()) {
                    if (checkWord()) {
                        if (currentTime > 12) {
                            point += 400;
                            if (!isHinted) {
                                Label speed = new Label("EXPEDITIOUS");
                                speed.setStyle("-fx-text-fill: black;" +
                                        "-fx-font-size: 18px;" +
                                        "-fx-font-family: cambria;" +
                                        "-fx-background-radius: 5;" +
                                        "-fx-background-color: white;");
                                AnchorPane.setTopAnchor(speed, 100.0);
                                AnchorPane.setLeftAnchor(speed, 710.0);
                                anchorPane.getChildren().add(speed);
                                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                                delay.setOnFinished(e -> {
                                    anchorPane.getChildren().remove(speed);
                                });
                                delay.play();
                            }
                        } else if (currentTime > 9) {
                            point += 300;
                            if (!isHinted) {
                                Label speed = new Label("SPEEDY");
                                speed.setStyle("-fx-text-fill: black;" +
                                        "-fx-font-size: 18px;" +
                                        "-fx-font-family: cambria;" +
                                        "-fx-background-radius: 5;" +
                                        "-fx-background-color: white;");
                                AnchorPane.setTopAnchor(speed, 100.0);
                                AnchorPane.setLeftAnchor(speed, 710.0);
                                anchorPane.getChildren().add(speed);
                                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                                delay.setOnFinished(e -> {
                                    anchorPane.getChildren().remove(speed);
                                });
                                delay.play();
                            }
                        } else if (currentTime > 6) {
                            point += 200;
                            if (!isHinted) {
                                Label speed = new Label("FAST");
                                speed.setStyle("-fx-text-fill: black;" +
                                        "-fx-font-size: 18px;" +
                                        "-fx-font-family: cambria;" +
                                        "-fx-background-radius: 5;" +
                                        "-fx-background-color: white;");
                                AnchorPane.setTopAnchor(speed, 100.0);
                                AnchorPane.setLeftAnchor(speed, 710.0);
                                anchorPane.getChildren().add(speed);
                                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                                delay.setOnFinished(e -> {
                                    anchorPane.getChildren().remove(speed);
                                });
                                delay.play();
                            }
                        } else point += 100;

                        pointLabel.setText(" Points  " + point);

                        setIcon("E:\\Java\\intellijJava\\OOPtemp\\WordSnatcher\\src\\main\\resources\\com\\example\\wordsnatcher\\image\\tick_icon.png");
                        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                            playStatusSound(true);
                        }

                        word++;
                        wordLabel.setText(" Words  " + word + " of 12");
                        PauseTransition delay = new PauseTransition(Duration.seconds(1));
                        delay.setOnFinished(e -> {
                            nextWord();
                        });
                        delay.play();

                    }
                }
            });
            gridPaneButton.add(btn, i, j);
            if (i == 3) {
                i = 0;
                j = 1;
            } else i++;
        }
    }

    /**
     * khi hết thời gian
     */
    public void handleTimeout() {
        if (!checkWord()) {
            showAnswer();
            setIcon("E:\\Java\\intellijJava\\OOPtemp\\WordSnatcher\\src\\main\\resources\\com\\example\\wordsnatcher\\image\\x_icon.png");
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                playStatusSound(false);
            }


            // dừng 1 giây rồi mới chuyển sang từ tiếp theo
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(e -> {
                word++;
                wordLabel.setText(" Words  " + word + " of 12");
                nextWord();
            });
            delay.play();
        }
    }

    /**
     * khi click nút Hint
     */
    @FXML
    public void hintWord() {
        isHinted = true;
        point -= 50;
        pointLabel.setText(" Points  " + point);
        String target = currentWord.getTarget();
        String result = "";

        for (Node node : flowPaneResult.getChildren()) {
            Button btn = (Button) node;
            result += btn.getText();
        }
        result += " ";

        int minLen = Math.min(result.length(), target.length());

        // Tìm vị trí ký tự khác nhau đầu tiên
        int diff = -1;
        for (int i = 0; i < minLen; i++) {
            if (result.charAt(i) != target.charAt(i)) {
                diff = i;
                break;
            }
        }

        if (diff != -1) {
            // Tạo một bản sao của danh sách các nút trong flowPaneResult
            List<Node> nodesToRemove = new ArrayList<>(flowPaneResult.getChildren().subList(diff, flowPaneResult.getChildren().size()));

            // Chuyển các button từ vị trí diffIndex đến hết trong flowPaneResult về gridPaneButton
            flowPaneResult.getChildren().removeAll(nodesToRemove);
            gridPaneButton.getChildren().addAll(nodesToRemove);

            // Chuyển nút có ký tự tại vị trí khác nhau đó từ gridPaneButton đến flowPaneResult
            char hintChar = target.charAt(diff);
            Button hintButton = findButton(hintChar, gridPaneButton);
            gridPaneButton.getChildren().remove(hintButton);
            flowPaneResult.getChildren().add(hintButton);
        }
    }

    /**
     * tìm button
     */
    public Button findButton(char c, GridPane gridPane) {
        ObservableList<Node> buttons = gridPane.getChildren();
        for (Node node : buttons) {
            Button btn = (Button) node;
            if (btn.getText().charAt(0) == c) return btn;
        }
        return null;
    }

    /**
     * chuyển tới giao diện kết quả
     */
    public void resultScreen() throws IOException {
        AnchorPane component = new AnchorPane();
        component.setPrefSize(809, 466);
        component.setStyle("-fx-background-color: #EF75D5;" +
                "-fx-background-radius: 10;");

        // thêm gridPane
        GridPane resultPane = new GridPane();
        resultPane.setHgap(40);
        resultPane.setVgap(20);

        int rowCount = 3;
        int columnCount = 4;
        int cnt = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Label text = new Label(randomWord.get(cnt).getTarget());
                text.setStyle("-fx-background-color: #863FA8;" +
                        "-fx-font-family: cambria;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20;" +
                        "-fx-font-weight: bold;");

                text.setAlignment(Pos.CENTER);
                text.setWrapText(true);
                text.setPrefSize(100, 50);

                int finalCnt = cnt;
                text.setOnMouseClicked(event -> {
                    showWordInformation(finalCnt);
                });
                cnt++;
                resultPane.add(text, j, i);

                // Đặt ràng buộc vị trí cho nút trong GridPane
                GridPane.setConstraints(text, j, i);
            }
        }
        setLocation(resultPane, component, 120.0, 160.0);

        // thêm label
        Label onTopLabel = new Label("Click a word to review!");
        onTopLabel.setStyle("-fx-font-size: 24px;" +
                "-fx-font-family: cambria;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;" +
                "-fx-text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);");
        setLocation(onTopLabel, component, 30.0, 295.0);

        // thêm nút
        Button doneButton = new Button("Done");
        doneButton.setPrefSize(80, 50);
        doneButton.setStyle("-fx-font-size: 20px;" +
                "-fx-font-family: cambria;" +
                "-fx-background-radius: 20;" +
                "-fx-font-weight: bold;");
        doneButton.setOnAction(event -> {
            AnchorPane endAnchorPane;
            try {
                if (mediaPlayer != null) mediaPlayer.stop();
                endAnchorPane = FXMLLoader.load(getClass().getResource("view/end.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(endAnchorPane);
        });
        setLocation(doneButton, component, 380, 380);

        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(component);

    }

    /**
     * show word's information
     */
    public void showWordInformation(int index) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(randomWord.get(index).getTarget());
        alert.setContentText(randomWord.get(index).getExplain());
        alert.show();
    }

    /**
     * hiển thị icon
     */
    public void setIcon(String path) {
        ImageView icon = new ImageView();
        icon.setImage(new Image(path));
        icon.setFitHeight(40);
        icon.setFitWidth(40);

        setLocation(icon, anchorPane, 320.0, 380.0);

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            anchorPane.getChildren().remove(icon);
        });
        delay.play();
    }

    /**
     * set vị trí cho Node và thêm node vào AnchorPane
     */
    public void setLocation(Node node, AnchorPane anchorPane, double top, double left) {
        AnchorPane.setTopAnchor(node, top);
        AnchorPane.setLeftAnchor(node, left);
        anchorPane.getChildren().add(node);
    }

    /**
     * bật / tắt sound
     */
    public void mutedSound(ActionEvent event){
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
            mediaPlayer.pause();
        }
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED){
            mediaPlayer.play();
        }
    }

    /**
     * x_ tick_
     */
    public void playStatusSound(boolean state){
        String path = "";
        if (state == true){
            path = "sound/win.wav";
        } else path = "sound/fail.wav";

        Media sound = new Media(getClass().getResource(path).toString());
        MediaPlayer mediaPlayer1 = new MediaPlayer(sound);
        mediaPlayer1.setCycleCount(1);
        mediaPlayer1.play();
    }

    /**
     * init
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaPlayer.stop();
        try {
            readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        explainTextArea.setEditable(false);
        explainTextArea.setWrapText(true);
        explainTextArea.setStyle("-fx-font-size: 20px;" +
                "-fx-font-family: 'Cambria';" +
                "-fx-border-color: none;" +
                "-fx-border-color: transparent;" +
                "-fx-border-width: 0;");

        timeProgressBar.setStyle("-fx-accent: #EF75D5;");

        playSound("sound/play.wav", 2);

        gridPaneButton.setHgap(3);
        gridPaneButton.setVgap(3);

        randomWord();
        setLocation(gridPaneButton, anchorPane, 330.0, 280.0);
        runGame();
    }
}