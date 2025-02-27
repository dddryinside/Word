package com.dddryinside.page;

import com.dddryinside.model.User;
import com.dddryinside.service.DataBaseAccess;
import com.dddryinside.service.Validator;
import com.dddryinside.contract.Page;
import com.dddryinside.element.VPane;
import com.dddryinside.service.PageManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class RegPage implements Page {
    @Override
    public Scene getInterface() {
        FontIcon personIcon = new FontIcon();
        personIcon.setIconSize(35);
        personIcon.setIconLiteral("bi-person-plus-fill");
        VBox.setMargin(personIcon, new Insets(0, 0, 20, 0));

        TextField nameField = new TextField();
        nameField.setMaxWidth(250);
        nameField.setPromptText("фамилия и имя");

        TextField usernameField = new TextField();
        usernameField.setMaxWidth(250);
        usernameField.setPromptText("username");

        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(250);
        passwordField.setPromptText("пароль");

        PasswordField repeatPasswordField = new PasswordField();
        repeatPasswordField.setMaxWidth(250);
        repeatPasswordField.setPromptText("повторите пароль");

        CheckBox stayAuthorised = new CheckBox("Оставаться авторизованным");
        stayAuthorised.setMinWidth(250);

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
                    User user = new User();

                    user.setName(nameField.getText());
                    user.setUsername(usernameField.getText());
                    user.setPassword(passwordField.getText());
                    user.setAuthorised(stayAuthorised.isSelected());

                    DataBaseAccess.saveUser(user);
                }
            }
        });

        HBox buttons = new HBox(logInButton, regButton);
        VBox.setMargin(buttons, new Insets(20, 0, 0, 0));
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setMaxWidth(250);
        buttons.setSpacing(20);

        VPane vPane = new VPane(personIcon, nameField, usernameField, passwordField, repeatPasswordField, stayAuthorised, buttons);
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
}
