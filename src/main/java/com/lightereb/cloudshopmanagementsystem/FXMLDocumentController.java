package com.lightereb.cloudshopmanagementsystem;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.*;

public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField fg_answer;

    @FXML
    private Button fg_backBtn;

    @FXML
    private AnchorPane fg_form;

    @FXML
    private Button fg_proceedBtn;

    @FXML
    private ComboBox<String> fg_question;

    @FXML
    private TextField fg_userName;

    @FXML
    private Button np_backBtn;

    @FXML
    private AnchorPane np_form;

    @FXML
    private PasswordField np_passConfirm;

    @FXML
    private PasswordField np_password;

    @FXML
    private Button np_proceedBtn;

    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_alreadyBtn;

    @FXML
    private Button side_createBtn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_confirmPass;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<String> su_question;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private Button su_singUpBtn;

    @FXML
    private TextField su_username;


    private Boolean flag = false;

    private Alert alert;

    private Connection con;

    /**
     * 注册
     */
    public void regBtn() {
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty() || su_confirmPass.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("请填写所有信息");
            alert.showAndWait();
        } else if (!su_password.getText().equals(su_confirmPass.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("密码不一致，请确认");
            alert.showAndWait();
        } else if (su_password.getText().length() < 8) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("密码至少是8位");
            alert.showAndWait();
        } else {
            DataBaseUtil.regUsers(
                    su_username.getText(),
                    su_password.getText(),
                    su_question.getSelectionModel().getSelectedItem(),
                    su_answer.getText(),
                    new Date(new java.util.Date().getTime())
            );
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("信息");
            alert.setHeaderText(null);
            alert.setContentText("用户注册成功，请登录。");
            alert.showAndWait();

            //清除
            su_username.setText("");
            su_password.setText("");
            su_confirmPass.setText("");
            su_question.getSelectionModel().clearSelection();
            su_answer.setText("");
            flag = true;
            switchForm(new ActionEvent());
        }
    }

    /**
     * 忘记密码
     */
    public void forgotPassBtn() {
        si_loginForm.setVisible(false);
        fg_form.setVisible(true);
    }

    /**
     * 返回登录页
     */
    public void backToLogin() {
        fg_form.setVisible(false);
        si_loginForm.setVisible(true);
    }

    /**
     * 返回忘记密码页
     */
    public void backToForgotPass() {
        np_form.setVisible(false);
        fg_form.setVisible(true);
    }

    /**
     * 登录
     */
    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("请输入用户名或密码");
            alert.showAndWait();
        } else {
            Map<String, Object> userInfo = DataBaseUtil.userInfo(si_username.getText(), si_password.getText());
            if (!userInfo.isEmpty()) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("成功");
                alert.setHeaderText(null);
                alert.setContentText("登录成功");
                alert.showAndWait();
                System.out.printf(userInfo.toString());
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("失败");
                alert.setHeaderText(null);
                alert.setContentText("用户或密码错误");
                alert.showAndWait();
            }
        }
    }

    private final String[] questionList = {"妈妈的姓名？", "爸爸的姓名？", "童年的昵称？", "第一个宠物的名字？"};

    public void regQuestionList() {

        List<String> listQ = new ArrayList<>(Arrays.asList(questionList));
        ObservableList<String> observableList = FXCollections.observableArrayList(listQ);
        su_question.setItems(observableList);
        fg_question.setItems(observableList);
    }

    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == side_createBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.3));

            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyBtn.setVisible(true);
                side_createBtn.setVisible(false);
            });
            slider.play();
        } else if (event.getSource() == side_alreadyBtn || flag) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.3));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyBtn.setVisible(false);
                side_createBtn.setVisible(true);
            });
            slider.play();
            flag = false;
        }
    }

    /**
     * 初始化DB
     */
    private void initDatabase() {
        con = DataBaseUtil.getConnection();
        if (con == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("数据库连接错误");
            alert.showAndWait();
        }
    }

    /**
     * 跳转新密码页
     */
    private void goToNewPassForm() {
        String question = fg_question.getSelectionModel().getSelectedItem();
        String answer = fg_answer.getText();
        String user = fg_userName.getText();
        if (question.isEmpty() || answer.isEmpty() || user.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("请输入所有信息");
        }else {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDatabase();
        su_question.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(su_question.getPromptText());
                } else {
                    setText(item);
                }
            }
        });
        regQuestionList();
    }
}