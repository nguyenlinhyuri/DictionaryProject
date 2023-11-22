package com.example.mydictionary;

import com.example.mydictionary.basic.DictionaryManagement;
import com.example.mydictionary.basic.Word;
import com.example.mydictionary.search.Dictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.mydictionary.api.SpeechToTextAPI.SoundEn;

public class MainScene extends AppUtils implements Initializable {
    @FXML
    private ImageView avtImageView;

    @FXML
    private Button searchButton;

    @FXML
    private Button translateButton;

    @FXML
    private Button practiceButton;

    @FXML
    private Button gameButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label usernameLabel;

    /**
     * search
     */
    @FXML
    private TextField inputWord;

    @FXML
    private ListView<String> listWord;

    @FXML
    private WebView viewTaget;


    private static final String DATA_FILE_PATH = "data/E_V.txt";

    private Dictionary diction = new Dictionary();
    private DictionaryManagement dictionaryFunction = new DictionaryManagement();
    // list dùng cho listView
    ObservableList<String> list = FXCollections.observableArrayList();
    private int indexOfSelectedWord; // thứ tự của từ được chọn

    private int firstIndexOfListFound = 0;

    public Dictionary getDiction() {
        return diction;
    }


    /**
     * click vào avt
     */
    public void clickAvt(MouseEvent mouseEvent) {
        ContextMenu option = new ContextMenu();
        MenuItem setAvtMenuItem = new MenuItem("Update avatar");
        setAvtMenuItem.setOnAction(event -> chooseAvatar());
        MenuItem deleteAvtMenuItem = new MenuItem("Delete avatar");
        deleteAvtMenuItem.setOnAction(event -> deleteAvatar());

        option.getItems().addAll(setAvtMenuItem, deleteAvtMenuItem);
        if (mouseEvent.getClickCount() == 1) {
            // hiển thị option tại vị trí avt
            option.show(avtImageView, mouseEvent.getSceneX() + 200, mouseEvent.getSceneY() + 50);
        }
    }

    /**
     * chọn ảnh từ file explorer
     */
    public void chooseAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String img_path = selectedFile.toURI().toString();
            Image selectedImg = new Image(img_path);
            avtImageView.setImage(selectedImg);
        }
    }

    /**
     * xóa ảnh đại diện
     */
    public void deleteAvatar() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete your avatar?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            setDefaultAvatar();
        }
    }

    /**
     * khi delete avt sẽ trở về avt mặc định
     */
    public void setDefaultAvatar() {
        try {
            Image defaultAvatar = new Image(getClass().getResourceAsStream("/com/example/mydictionary/image/avtDefault.png"));
            avtImageView.setImage(defaultAvatar);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    /**
     * click Search
     */
    public void searchAction(ActionEvent event) {
        if (mediaPlayer != null) mediaPlayer.stop();


        if (isGameScene) {
            rootAnchorPane.getChildren().remove(gameAnchorPane);
            isGameScene = false;
        } else if (isPracticeScene) {
            rootAnchorPane.getChildren().remove(practiceAnchorPane);
            isPracticeScene = false;
        } else if (isTranslateScene) {
            rootAnchorPane.getChildren().remove(translateAnchorPane);
            isTranslateScene = false;
        }
    }

    /**
     * click Translator
     */
    public void translateAction(ActionEvent event) {
        if (isGameScene) {
            rootAnchorPane.getChildren().remove(gameAnchorPane);
            isGameScene = false;
        } else if (isPracticeScene) {
            rootAnchorPane.getChildren().remove(practiceAnchorPane);
            isPracticeScene = false;
        }

        if (!isTranslateScene) {
            if (mediaPlayer != null) mediaPlayer.stop();
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = AppUtils.class.getResource("view/TranslateText.fxml");
            fxmlLoader.setLocation(url);
            try {
                translateAnchorPane = fxmlLoader.load();
                AnchorPane.setTopAnchor(translateAnchorPane, top);
                AnchorPane.setLeftAnchor(translateAnchorPane, left);
                rootAnchorPane.getChildren().add(translateAnchorPane);
                isTranslateScene = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * click Practice
     */
    public void practiceAction(ActionEvent event) {
        if (mediaPlayer != null) mediaPlayer.stop();

        if (isGameScene) {
            rootAnchorPane.getChildren().remove(gameAnchorPane);
            isGameScene = false;
        } else if (isTranslateScene) {
            rootAnchorPane.getChildren().remove(translateAnchorPane);
            isTranslateScene = false;
        }

        if (!isPracticeScene) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = AppUtils.class.getResource("view/practice.fxml");
            fxmlLoader.setLocation(url);
            try {
                practiceAnchorPane = fxmlLoader.load();
                AnchorPane.setTopAnchor(practiceAnchorPane, top);
                AnchorPane.setLeftAnchor(practiceAnchorPane, left);
                rootAnchorPane.getChildren().add(practiceAnchorPane);
                isPracticeScene = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * click Game
     */
    public void gameAction(ActionEvent event) {
        if (mediaPlayer != null) mediaPlayer.stop();

        if (isTranslateScene) {
            rootAnchorPane.getChildren().remove(translateAnchorPane);
            isTranslateScene = false;
        } else if (isPracticeScene) {
            rootAnchorPane.getChildren().remove(practiceAnchorPane);
            isPracticeScene = false;
        }

        if (!isGameScene) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = AppUtils.class.getResource("view/game.fxml");
            fxmlLoader.setLocation(url);
            try {
                gameAnchorPane = fxmlLoader.load();
                AnchorPane.setTopAnchor(gameAnchorPane, top);
                AnchorPane.setLeftAnchor(gameAnchorPane, left);
                rootAnchorPane.getChildren().add(gameAnchorPane);
                isGameScene = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * click Exit
     */
    public void exitAction(ActionEvent event) throws IOException {
        if (mediaPlayer != null) mediaPlayer.stop();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit the application?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            writeNotedWordData();
            System.exit(0);
        }
    }

    /**
     * khi gõ tìm kiếm từ trong textFied
     */
    @FXML
    private void handleInputText() {
        list.clear();
        String input = inputWord.getText().trim();
        list = dictionaryFunction.lookUp(diction, input);
        if (list.isEmpty()) {
            setListDefault(firstIndexOfListFound);
        } else {
            listWord.setItems(list);
            firstIndexOfListFound = dictionaryFunction.searchWord(diction, list.get(0));
        }
    }

    /**
     * khi click chonj 1 tu trong list view
     */
    @FXML
    private void handleMouseClickaWord(MouseEvent arg0) {

        String selectedWord = listWord.getSelectionModel().getSelectedItem();  //  từ đã click
        if (selectedWord != null) {
            indexOfSelectedWord = dictionaryFunction.searchWord(diction, selectedWord);// lấy chỉ số của từ đó trong từ điển
            inputWord.setText(selectedWord);
            if (indexOfSelectedWord == -1) { // nếu không thấy
//                System.out.println("fail");
                return;
            }


            viewTaget.getEngine().loadContent((diction.get(indexOfSelectedWord).getExplain()), "text/html");

        }
    }

    @FXML
    public void submit(ActionEvent e) {
        String input = inputWord.getText();
        int index = dictionaryFunction.searchWord(diction, input);
        viewTaget.getEngine().loadContent(diction.get(index).getExplain(), "text/html");
    }

    /**
     * khi click vô nút phát âm.
     */

    @FXML
    public void pronounce(ActionEvent e) {
        String text = inputWord.getText();
        SoundEn(text);
    }
    @FXML
    public void addWord(ActionEvent e)
    {
        if (inputWord.getText().isEmpty()){
            showAlertInformation("Oops!", "Please choose a word");
            return;
        }
        String word = inputWord.getText();
        AtomicReference<String> def = new AtomicReference<>("");
        Word w = new Word();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Noted Word");
        dialog.setHeaderText(word);
        dialog.setContentText("Your meaning: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(meaning -> {
            def.set(meaning);
        });

        w.setTarget(word);
        w.setExplain(def.get());
        notedWord.put(w.getTarget(), w.getExplain());
//        System.out.println(w.getTarget());
//        System.out.println(w.getExplain());

    }


    /**
     * tạo 1 danh sách cho list view
     *
     * @param ind (index) vị trí.
     */
    private void setListDefault(int ind) {
        list.clear();
        for (int i = ind; i < ind + 15; i++) {
            list.add(diction.get(i).getTarget());
        }
        listWord.setItems(list);
        viewTaget.getEngine().loadContent(diction.get(ind).getExplain(), "text/html");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(USER_NAME);
        dictionaryFunction.readFromFile(diction, DATA_FILE_PATH);
//        System.out.println(diction.size());
        // dẩy dữ liệu vào trie
        dictionaryFunction.setTrie(diction);

        setListDefault(0);
        inputWord.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (inputWord.getText().isEmpty()) {
                    setListDefault(0);
                } else {
                    handleInputText();
                }
            }
        });
    }
}
