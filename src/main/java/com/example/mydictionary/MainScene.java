package com.example.mydictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
            option.show(avtImageView, mouseEvent.getSceneX(), mouseEvent.getSceneY());
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
            Image defaultAvatar = new Image("image/avtDefault.png");
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

        if (!isSearchScene) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = AppUtils.class.getResource("search/TranslateWord.fxml");
            fxmlLoader.setLocation(url);
            try {
                searchAnchorPane = fxmlLoader.load();
                AnchorPane.setTopAnchor(searchAnchorPane, top);
                AnchorPane.setLeftAnchor(searchAnchorPane, left);
                rootAnchorPane.getChildren().add(searchAnchorPane);
                isSearchScene = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * click Translate text
     */
    public void translateAction(ActionEvent event) {
        if (isGameScene) {
            rootAnchorPane.getChildren().remove(gameAnchorPane);
            isGameScene = false;
        } else if (isPracticeScene) {
            rootAnchorPane.getChildren().remove(practiceAnchorPane);
            isPracticeScene = false;
        } else if (isSearchScene) {
            rootAnchorPane.getChildren().remove(searchAnchorPane);
            isSearchScene = false;
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
        } else if (isSearchScene) {
            rootAnchorPane.getChildren().remove(searchAnchorPane);
            isSearchScene = false;
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
        } else if (isSearchScene) {
            rootAnchorPane.getChildren().remove(searchAnchorPane);
            isSearchScene = false;
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
    public void exitAction(ActionEvent event) {
        if (mediaPlayer != null) mediaPlayer.stop();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit the application?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(USER_NAME);
    }
}
