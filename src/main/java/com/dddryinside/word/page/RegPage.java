package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.model.User;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.Validator;
import com.dddryinside.word.value.AppColor;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegPage implements Page {
    @Override
    public Scene getInterface() {
        JFXTextField nameField = new JFXTextField();
        nameField.setFocusColor(AppColor.BLUE.getColor());
        nameField.setMaxWidth(250);
        nameField.setPromptText("фамилия и имя");

        JFXTextField usernameField = new JFXTextField();
        usernameField.setFocusColor(AppColor.BLUE.getColor());
        usernameField.setMaxWidth(250);
        usernameField.setPromptText("username");

        JFXPasswordField passwordField = new JFXPasswordField();
        passwordField.setFocusColor(AppColor.BLUE.getColor());
        passwordField.setMaxWidth(250);
        passwordField.setPromptText("пароль");

        JFXPasswordField repeatPasswordField = new JFXPasswordField();
        repeatPasswordField.setFocusColor(AppColor.BLUE.getColor());
        repeatPasswordField.setMaxWidth(250);
        repeatPasswordField.setPromptText("повторите пароль");

        JFXCheckBox stayAuthorisedBox = new JFXCheckBox("Оставаться авторизованным");
        stayAuthorisedBox.setMinWidth(250);
        stayAuthorisedBox.setCheckedColor(AppColor.BLUE.getColor());

        Hyperlink logInButton = new Hyperlink("Отмена");
        logInButton.setOnAction(event -> PageManager.loadPage(new LogInPage()));
        Hyperlink regButton = new Hyperlink("Зарегистрироваться");
        regButton.setOnAction(event -> {
            if (Validator.isNameValid(nameField.getText()) &&
                    Validator.isUsernameValid(usernameField.getText()) &&
                    Validator.isPasswordValid(passwordField.getText())) {
                if (!passwordField.getText().equals(repeatPasswordField.getText())) {
                    PageManager.showNotification("Пароли не совпадают!");
                } else {
                    User user = new User(nameField.getText(), usernameField.getText(),
                            passwordField.getText(), stayAuthorisedBox.isSelected());

                    DataBaseAccess.saveUser(user);
                }
            }
        });

        HBox buttons = new HBox(logInButton, regButton);
        buttons.setMaxWidth(250);
        buttons.setSpacing(20);

        VBox container = new VBox(nameField, usernameField, passwordField, repeatPasswordField, stayAuthorisedBox, buttons);
        container.setSpacing(20);
        container.setAlignment(Pos.CENTER);

        return new Scene(container);
    }
}
