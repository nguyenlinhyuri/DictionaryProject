package com.example.mydictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
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
    public void clickAvt(MouseEvent mouseEvent){
        ContextMenu option = new ContextMenu();
        MenuItem setAvtMenuItem = new MenuItem("Update avatar");
        setAvtMenuItem.setOnAction(event -> chooseAvatar());
        MenuItem deleteAvtMenuItem = new MenuItem("Delete avatar");
        deleteAvtMenuItem.setOnAction(event -> deleteAvatar());

        option.getItems().addAll(setAvtMenuItem, deleteAvtMenuItem);
        if (mouseEvent.getClickCount() == 1){
            // hiển thị option tại vị trí avt
            option.show(avtImageView, mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }
    }

    /**
     * chọn ảnh từ file explorer
     */
    public void chooseAvatar(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null){
            String img_path = selectedFile.toURI().toString();
            Image selectedImg = new Image(img_path);
            avtImageView.setImage(selectedImg);
        }
    }

    /**
     * xóa ảnh đại diện
     */
    public void deleteAvatar(){
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
            Image defaultAvatar = new Image("E:\\Java\\intellijJava\\OOPtemp\\MyDictionary\\src\\main\\resources\\com\\example\\mydictionary\\image\\avtDefault.png");
            avtImageView.setImage(defaultAvatar);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

    }

    /**
     * click Search
     */
    public void searchAction(ActionEvent event){

    }

    /**
     * click Translate text
     */
    public void translateAction(ActionEvent event){

    }

    /**
     * click Practice
     */
    public void practiceAction(ActionEvent event){
        showNewScene(rootAnchorPane, "view/practice.fxml", top, left);
    }

    /**
     * click Game
     */
    public void gameAction(ActionEvent event){
        showNewScene(rootAnchorPane, "view/game.fxml", top, left);

    }

    /**
     * click Exit
     */
    public void exitAction(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit the application?");

        if (alert.showAndWait().get() == ButtonType.OK){
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(USER_NAME);
    }
}
