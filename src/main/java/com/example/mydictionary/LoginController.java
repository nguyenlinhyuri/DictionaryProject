package com.example.mydictionary;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LoginController extends AppUtils implements Initializable {
    @FXML
    private AnchorPane rootLogin;

    /**
     * Log In
     */
    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passField;

    @FXML
    private Button loginButton;

    @FXML
    private Label signupLabel;

    /**
     * Sign Up
     */
    @FXML
    private AnchorPane signupAnchorPane;

    @FXML
    private Button signupButton;

    @FXML
    private TextField signupUserTextField;

    @FXML
    private PasswordField signupPassField;

    @FXML
    private Label loginLabel;


    private Map<String, String> info_user = new HashMap<>();
    private String user_path = "E:\\Java\\intellijJava\\OOPtemp\\MyDictionary\\src\\main\\resources\\com\\example\\mydictionary\\data\\info.txt";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * đọc dữ liệu người dùng vtwf file vào mảng
     */
    public void readData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(user_path));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\t");
            if (parts.length >= 2) {
                info_user.put(parts[0], parts[1]);
            }
        }
        for (Map.Entry entry : info_user.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue());
        }
        System.out.println("read successfully!");
    }

    /**
     * kiểm tra thông tin đăng nhập, ấn login, chuyển sang giao diện chính
     */
    @FXML
    public void loginAction(ActionEvent event) throws IOException {
        String user = userTextField.getText().trim();
        String pass = passField.getText().trim();

        // nhập thiếu
        if (user.isEmpty() || pass.isEmpty()) {
            showAlertInformation("Oops!", "Please enter full information!");
        } else if (pass.length() < 6) {
            // pass quá ngắn
            showAlertInformation("Oops!", "Password must have more than 6 characters.");
        } else if (!info_user.containsKey(user) || !info_user.get(user).equals(pass)) {
            // nhập sai
            showAlertInformation("Oops!", "Account or password is NOT correct!");
        } else if (info_user.containsKey(user) && info_user.get(user).equals(pass)) {
            // nhập đúng
            USER_NAME = user;
            showAlertInformation("Congratulation!", "Successful log in!");
            rootAnchorPane = FXMLLoader.load(getClass().getResource("view/mainScene.fxml"));
//            rootAnchorPane = (AnchorPane) root;
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(rootAnchorPane);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * chưa có tài khoản, ấn Sign up
     */
    @FXML
    public void signupClick(MouseEvent mouseEvent) {
        showNewScene(rootLogin, "view/signup.fxml", 50.0, 383.0);
    }

    /**
     * đăng ký xong, ấn Sign Up
     */
    @FXML
    public void signupAction(ActionEvent event) throws IOException {
        String user = signupUserTextField.getText().trim();
        String pass = signupPassField.getText().trim();

        if (info_user.containsKey(user) && info_user.get(user).equals(pass)) {
            // tài khoản đã tồn tại
            showAlertInformation("Oops!", "Account already exists!");
        } else if (pass.length() < 6) {
            // pass quá ngắn
            showAlertInformation("Oops!", "Password must have more than 6 characters");
        } else {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(user_path))){
                bw.write(user + "\t" + pass);
                bw.newLine();
                bw.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            showAlertInformation("Congratulation!", "You have successfully registered your account!");
        }

        root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * đã có tài khoản, ấn Log In
     */
    @FXML
    public void loginClick(MouseEvent mouseEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}