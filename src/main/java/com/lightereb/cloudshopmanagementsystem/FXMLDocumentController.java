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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {
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

    public void regBtn() {
        Alert alert;
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

    private final String[] questionList = {"妈妈的姓名？", "爸爸的姓名？", "童年的昵称？", "第一个宠物的名字？"};

    public void regQuestionList() {

        List<String> listQ = new ArrayList<>(Arrays.asList(questionList));
        ObservableList<String> observableList = FXCollections.observableArrayList(listQ);
        su_question.setItems(observableList);
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
            regQuestionList();
            flag = false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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