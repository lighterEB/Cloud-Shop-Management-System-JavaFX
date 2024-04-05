package com.lightereb.cloudshopmanagementsystem;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.w3c.dom.ls.LSException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FXMLDocumentController {
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
    private PasswordField su_answer;

    @FXML
    private PasswordField su_confirmPass;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private Button su_singupBtn;

    @FXML
    private TextField su_username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet resultSet;

    private final String[] questionList = {"妈妈的姓名？", "爸爸的姓名？", "童年的昵称？", "第一个宠物的名字？"};

    public void regQuestionList() {

        List<String> listQ = new ArrayList<String>(Arrays.asList(questionList));
        ObservableList observableList = FXCollections.observableArrayList(listQ);
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
            regQuestionList();
        } else if (event.getSource() == side_alreadyBtn) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.3));

            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyBtn.setVisible(false);
                side_createBtn.setVisible(true);
            });
            slider.play();
        }
    }
    @FXML
    void switForm(ActionEvent event) {

    }
}