package com.example.mydictionary.practice;

import com.example.mydictionary.AppUtils;
import com.example.mydictionary.Practice;
import com.example.mydictionary.basic.Word;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Exercises extends Practice implements Initializable {
    @FXML
    private Button button_A;

    @FXML
    private Button button_B;

    @FXML
    private Button button_C;

    @FXML
    private Button button_D;

    @FXML
    private Button stopPracticeButton;

    @FXML
    private Label quizIndexLabel;

    @FXML
    private TextArea explainTextArea;

    @FXML
    private Label scoreLabel;

    private List<Word> vocabList = new ArrayList<>();
    //    private List<String> wrongWords = new ArrayList<>();
    private int currentIndex;
    private int correctAnswerIndex;
    private List<Button> ansButtonList = new ArrayList<>();
//    private JdbcDao jdbcDao = new JdbcDao();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quizIndexLabel.setText("Question " + currentIndex);
        explainTextArea.setWrapText(true);
        loadVocabList();
        startPractice();
    }

    /**
     * bắt đầu luyện tập
     */
    public void startPractice() {
        Collections.shuffle(vocabList);
        currentIndex = 0;
        ansButtonList.add(button_A);
        ansButtonList.add(button_B);
        ansButtonList.add(button_C);
        ansButtonList.add(button_D);
        askQuestion();
    }

    /**
     * cập nhật List từ vựng
     */
    public void loadVocabList() {
        for (Map.Entry entry : notedWord.entrySet()) {
            vocabList.add(new Word((String) entry.getKey(), (String) entry.getValue()));
        }
    }

    /**
     * xử lý trong mỗi câu hỏi
     */
    public void askQuestion() {
        ansButtonList.forEach(button -> {
//            button.setDisable(false);
            button.setStyle("-fx-background-color: white");
        });

        if (currentIndex < vocabList.size()) {
            String currentWord = vocabList.get(currentIndex).getTarget();

            // lấy ra 3 đáp án sai
            List<String> answerOptions = createAnswerOptions(currentWord);

            // đưa nghĩa lên text area
            explainTextArea.setText(notedWord.get(currentWord));

            // random 1 nút làm đáp án đúng và set từ cho nút đó
            correctAnswerIndex = new Random().nextInt(4);
            ansButtonList.get(correctAnswerIndex).setText(currentWord);

            // đặt 3 dáp án sai
            int optionIndex = 0;
            for (int i = 0; i < 4; i++) {
                if (i != correctAnswerIndex) {
                    ansButtonList.get(i).setText(answerOptions.get(optionIndex));
                    optionIndex++;
                }
            }

            button_A.setOnAction(event -> handleAnswer(0));
            button_B.setOnAction(event -> handleAnswer(1));
            button_C.setOnAction(event -> handleAnswer(2));
            button_D.setOnAction(event -> handleAnswer(3));

        } else {
            //  hiển thị giao diện chúc mừng
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = AppUtils.class.getResource("practice/congratulation.fxml");
            fxmlLoader.setLocation(url);
            try {
                endexercisesAnchorPane = fxmlLoader.load();
                AnchorPane.setTopAnchor(exercisesAnchorPane, top1);
                AnchorPane.setLeftAnchor(endexercisesAnchorPane, left1);
                exercisesAnchorPane.getChildren().add(endexercisesAnchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            ansButtonList.forEach(button -> button.setDisable(true));
        }
    }

    /**
     * tạo đáp án
     */
    public List<String> createAnswerOptions(String currentWord) {
        List<String> answerOptions = new ArrayList<>(notedWord.keySet());
        answerOptions.remove(currentWord);
        Collections.shuffle(answerOptions);

        // trả về 3 từ bất kỳ
        return answerOptions.subList(0, 3);
    }

    /**
     * xử lý khi chọn đáp án
     */
    public void handleAnswer(int selectedButtonIndex) {
        Button selectedButton = ansButtonList.get(selectedButtonIndex);
        for (Button button : ansButtonList) {
//            button.setDisable(true);
        }

        String selectedAnswer = selectedButton.getText();
        String correctAnswer = vocabList.get(currentIndex).getTarget();

        if (selectedAnswer.equals(correctAnswer)) {
            selectedButton.setStyle("-fx-background-color: #05C5CA");
            numOfWordsPracticed++;
            if (numOfWordsPracticed == 1) scoreLabel.setText("  Your word  " + numOfWordsPracticed);
            scoreLabel.setText("  Your words  " + numOfWordsPracticed);
        } else {
//            wrongWords.add(correctAnswer);
            ansButtonList.get(correctAnswerIndex).setStyle("-fx-background-color: #05C5CA");
            selectedButton.setStyle("-fx-background-color: red");
        }

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            currentIndex++;
            quizIndexLabel.setText("Quiz " + currentIndex);
            askQuestion();
        });
        delay.play();
    }

    /**
     * dừng luyện tập
     */
    public void stopPracticeAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("You are sure to stop practicing?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = AppUtils.class.getResource("practice/congratulation.fxml");
            fxmlLoader.setLocation(url);
            try {
                endexercisesAnchorPane = fxmlLoader.load();
                AnchorPane.setTopAnchor(exercisesAnchorPane, top1);
                AnchorPane.setLeftAnchor(endexercisesAnchorPane, left1);
                exercisesAnchorPane.getChildren().add(endexercisesAnchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
