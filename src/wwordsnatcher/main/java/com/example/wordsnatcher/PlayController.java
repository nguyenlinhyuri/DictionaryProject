package com.example.wordsnatcher;

import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PlayController extends GameUtils implements Initializable {
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
        root = FXMLLoader.load(getClass().getResource("pause.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
            btn.setStyle("-fx-font-size: 18px;");
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
            btn.setStyle("-fx-font-size: 18px;");
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
                        if (currentTime > 15) {
                            point += 400;
                            if (!isHinted) {
                                Label speed = new Label("EXPEDITIOUS");
                                speed.setStyle("-fx-font-size: 16px;");
                                AnchorPane.setTopAnchor(speed, 76.0);
                                AnchorPane.setLeftAnchor(speed, 674.0);
                                anchorPane.getChildren().add(speed);
                                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                                delay.setOnFinished(e -> {
                                    anchorPane.getChildren().remove(speed);
                                });
                                delay.play();
                            }
                        } else if (currentTime > 10) {
                            point += 300;
                            if (!isHinted) {
                                Label speed = new Label("SPEEDY");
                                speed.setStyle("-fx-font-size: 16px;");
                                AnchorPane.setTopAnchor(speed, 76.0);
                                AnchorPane.setLeftAnchor(speed, 674.0);
                                anchorPane.getChildren().add(speed);
                                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                                delay.setOnFinished(e -> {
                                    anchorPane.getChildren().remove(speed);
                                });
                                delay.play();
                            }
                        } else if (currentTime > 5) {
                            point += 200;
                            if (!isHinted) {
                                Label speed = new Label("FAST");
                                speed.setStyle("-fx-font-size: 16px;");
                                AnchorPane.setTopAnchor(speed, 76.0);
                                AnchorPane.setLeftAnchor(speed, 674.0);
                                anchorPane.getChildren().add(speed);
                                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                                delay.setOnFinished(e -> {
                                    anchorPane.getChildren().remove(speed);
                                });
                                delay.play();
                            }
                        } else point += 100;

                        pointLabel.setText("Point  " + point);

                        setIcon("E:\\Java\\intellijJava\\OOPtemp\\WordSnatcher\\src\\main\\resources\\com\\example\\wordsnatcher\\tick_icon.png");
                        word++;
                        wordLabel.setText("Word  " + word + " of 12");
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
            setIcon("E:\\Java\\intellijJava\\OOPtemp\\WordSnatcher\\src\\main\\resources\\com\\example\\wordsnatcher\\x_icon.png");

            // dừng 1 giây rồi mới chuyển sang từ tiếp theo
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(e -> {
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
        pointLabel.setText("Point  " + point);
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
        component.setPrefSize(520, 800);

        // thêm gridPane
        GridPane resultPane = new GridPane();
        resultPane.setPrefSize(430, 230);
        resultPane.setHgap(10);
        resultPane.setVgap(10);

        int rowCount = 3;
        int columnCount = 4;
        int cnt = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                String s = status.get(cnt) + "\n" + randomWord.get(cnt).getTarget();
                TextArea text = new TextArea(s);
                text.setEditable(false);
                text.setWrapText(true);
                text.setPrefSize(180, 160);
                text.setStyle("-fx-font-size: 18px;");
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
        setLocation(resultPane, component, 100.0, 100.0);

        // thêm label
        Label onTopLabel = new Label("CLick a word to review!");
        onTopLabel.setStyle("-fx-font-size: 18px;");
        setLocation(onTopLabel, component, 10.0, 200.0);

        // thêm nút
        Button doneButton = new Button("Done");
        doneButton.setStyle("-fx-font-size: 18px;");
        doneButton.setOnAction(event -> {
            AnchorPane endAnchorPane;
            try {
                endAnchorPane = FXMLLoader.load(getClass().getResource("end.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            anchorPane.getChildren().clear();
            anchorPane.getChildren().add(endAnchorPane);
        });
        setLocation(doneButton, component, 450, 370);

        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(component);

        for (String x : status){
            System.out.print(x + " | ");
        }

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
        setLocation(icon, anchorPane, 285.0, 390.0);

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
     * init
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        explainTextArea.setEditable(false);
        explainTextArea.setWrapText(true);
        explainTextArea.setStyle("-fx-font-size: 18px;");

        randomWord();
        setLocation(gridPaneButton, anchorPane, 350.0, 280.0);
        runGame();
    }
}
