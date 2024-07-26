package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.value.AppColor;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

public class LogInPage implements Page {
    @Override
    public Scene getInterface() {
        JFXTextField usernameField = new JFXTextField();
        usernameField.setFocusColor(AppColor.BLUE.getColor());
        usernameField.setMaxWidth(250);
        usernameField.setPromptText("username");

        JFXPasswordField passwordField = new JFXPasswordField();
        passwordField.setFocusColor(AppColor.BLUE.getColor());
        passwordField.setMaxWidth(250);
        passwordField.setPromptText("пароль");

        JFXCheckBox stayAuthorised = new JFXCheckBox("Оставаться авторизованным");
        stayAuthorised.setMinWidth(250);
        stayAuthorised.setCheckedColor(AppColor.BLUE.getColor());

        Hyperlink logInButton = new Hyperlink("Войти");
        logInButton.setOnAction(event -> {
            if (stringIsEmpty(usernameField.getText())) {
                PageManager.showNotification("Кажется, вы забыли ввести username!");
            } else if (stringIsEmpty(passwordField.getText())) {
                PageManager.showNotification("Кажется, вы забыли ввести пароль!");
            } else {
                DataBaseAccess.logIn(usernameField.getText(), passwordField.getText(), stayAuthorised.isSelected());
            }
        });

        Hyperlink regButton = new Hyperlink("Зарегистрироваться");
        regButton.setOnAction(event -> PageManager.loadPage(new RegPage()));

        HBox buttons = new HBox(logInButton, regButton);
        buttons.setMaxWidth(250);
        buttons.setSpacing(20);

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(usernameField, passwordField, stayAuthorised, buttons);
        container.setSpacing(20);

        return new Scene(container);
    }

    private boolean stringIsEmpty(String string) {
        if (string == null || string.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}