package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.VPane;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.value.AppColor;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;

public class LogInPage implements Page {
    @Override
    public Scene getInterface() {
        FontIcon personIcon = new FontIcon();
        personIcon.setIconSize(35);
        personIcon.setIconLiteral("bi-person-fill");
        VBox.setMargin(personIcon, new Insets(0, 0, 20, 0));

        TextField usernameField = new TextField();
        usernameField.setMaxWidth(250);
        usernameField.setPromptText("username");

        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(250);
        passwordField.setPromptText("пароль");

        CheckBox stayAuthorised = new CheckBox("Оставаться авторизованным");
        stayAuthorised.setMinWidth(250);


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
        VBox.setMargin(buttons, new Insets(20, 0, 0, 0));
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setMaxWidth(250);
        buttons.setSpacing(20);


        VPane vPane = new VPane(personIcon, usernameField, passwordField, stayAuthorised, buttons);
        vPane.setSpacing(20);
        vPane.setShadow();
        vPane.setBorderRadius();
        vPane.setAlignment(Pos.CENTER);
        vPane.setPadding(new Insets(20));

        GridPane container = new GridPane();
        container.getChildren().add(vPane);
        container.setAlignment(Pos.CENTER);

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