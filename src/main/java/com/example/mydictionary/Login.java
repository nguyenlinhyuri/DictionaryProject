package com.example.mydictionary;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Login extends AppUtils implements Initializable {
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

    @FXML
    private Label forgotPassLabel;

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



    private List<String> user_info = new ArrayList<>();
    private String user_path = "data/info.txt";
    private String QUES = "";
    private String ANS = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * đọc dữ liệu người dùng từ file vào mảng
     */
    public void readData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(user_path));
        String line;
        while ((line = br.readLine()) != null) {
            user_info.add(line);
        }
        for (String x : user_info) System.out.println(x);
        System.out.println("read successfully!");
    }

    /**
     * kiểm tra thông tin đăng nhập, ấn login, chuyển sang giao diện chính
     */
    @FXML
    public void loginAction(ActionEvent event) throws IOException {
        if (user_info.isEmpty()) {
            showAlertInformation("Opps!", "Please sign up your account.");
        } else {
            String user = userTextField.getText().trim();
            String pass = passField.getText().trim();

            // nhập thiếu
            if (user.isEmpty() || pass.isEmpty()) {
                showAlertInformation("Oops!", "Please enter full information!");
            } else if (pass.length() < 6) {
                // pass quá ngắn
                showAlertInformation("Oops!", "Password must have more than 6 characters.");
            } else if (!user_info.get(0).equals(user) || !user_info.get(1).equals(pass)) {
                // nhập sai
                showAlertInformation("Oops!", "Account or password is NOT correct!");
            } else if (user_info.get(0).equals(user) && user_info.get(1).equals(pass)) {
                // nhập đúng
                USER_NAME = user;
                showAlertInformation("Congratulation!", "Successful log in!");
                rootAnchorPane = FXMLLoader.load(getClass().getResource("view/mainScene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(rootAnchorPane);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    /**
     * chưa có tài khoản, ấn Sign up
     */
    @FXML
    public void signupClick(MouseEvent mouseEvent) {
        if (!user_info.isEmpty()){
            showAlertInformation("Oops!", "You already have an account.");
        } else
            showNewScene(rootLogin, signupAnchorPane, "view/signup.fxml", 50.0, 383.0);
    }

    /**
     * quên mật khẩu
     */
    public void forgotPasswordAction(MouseEvent event){
        if (user_info.isEmpty()){
            showAlertInformation("Oops!", "Please sign up your account");
        } else
            showConfirmQuestion();

    }

    /**
     * đăng ký xong, ấn Sign Up
     */
    @FXML
    public void signupAction(ActionEvent event) throws IOException {
        String user = signupUserTextField.getText().trim();
        String pass = signupPassField.getText().trim();

        if (pass.length() < 6) {
            // pass quá ngắn
            showAlertInformation("Oops!", "Password must have more than 6 characters");
        } else {
            showConfirmQuestionList();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(user_path))) {
                bw.write(user);
                bw.newLine();
                bw.write(pass);
                bw.newLine();
                bw.write(QUES);
                bw.newLine();
                bw.write(ANS);
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showAlertInformation("Congratulation!", "You have successfully registered your account!");

            root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

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

    /**
     * hiển thị list các câu hỏi xác nhận
     */
    public void showConfirmQuestionList(){
        List <String> quesList = Arrays.asList("What color do you like?"
                , "Who is your idol?"
                , "Where are you from?"
                , "What food do you like?");

        ListView <String> quesListView = new ListView<>(FXCollections.observableArrayList(quesList));
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("CONFIRM");
        dialog.setHeaderText("Choose a question");
        dialog.setGraphic(quesListView);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(item -> {
            QUES = quesListView.getSelectionModel().getSelectedItem();
            dialog.getEditor().setPromptText(QUES);
            ANS = dialog.getEditor().getText();
        });
    }

    /**
     * hien thi cau hoi xac nhan
     */
    public void showConfirmQuestion (){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("CONFIRM");
        dialog.setHeaderText(QUES);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(item -> {
            String ans = dialog.getEditor().getText();
            if (ans.equals(ANS)){
                showNewScene(rootLogin, signupAnchorPane, "view/signup.fxml", 50.0, 383.0);
            } else showAlertInformation("Oops!", "Ban khong the dang nhap tren thiet bi nay");
        });
    }
}