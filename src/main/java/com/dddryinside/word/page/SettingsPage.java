package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.VPane;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.ResourceLoader;

import com.dddryinside.word.service.Validator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;

public class SettingsPage implements Page {
    @Override
    public Scene getInterface() {
        FontIcon settingsIcon = ResourceLoader.loadIcon("bi-gear", 35);
        VBox.setMargin(settingsIcon, new Insets(0, 0, 20, 0));


        Label trainingSettingsLabel = new Label("Тренировка");
        trainingSettingsLabel.setFont(Font.font(16));


        Label trainingLengthLabel = new Label("Длина тренировки (вопросов):");
        trainingLengthLabel.setWrapText(true);
        trainingLengthLabel.setFont(Font.font(14));
        Spinner<Integer> trainingLengthInput = new Spinner<>();
        trainingLengthInput.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, DataBaseAccess.getUser().getTrainingLength()));
        trainingLengthInput.setMaxWidth(80);

        HBox trainingLengthBox = new HBox(trainingLengthLabel, trainingLengthInput);
        trainingLengthBox.setSpacing(5);
        trainingLengthBox.setAlignment(Pos.BASELINE_CENTER);


        Label protectionSettingsLabel = new Label("Смена пароля");
        protectionSettingsLabel.setFont(Font.font(16));

        PasswordField oldPasswordInput = new PasswordField();
        oldPasswordInput.setPromptText("старый пароль");

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("новый пароль");

        PasswordField repeatNewPasswordField = new PasswordField();
        repeatNewPasswordField.setPromptText("повторите новый пароль");

        Hyperlink exitButton = new Hyperlink("Отмена");
        exitButton.setOnAction(event -> PageManager.loadPage(new MainPage()));

        Hyperlink saveButton = new Hyperlink("Сохранить");
        saveButton.setOnAction(event -> {
            boolean correctUpdateConditions = false;

            if (!oldPasswordInput.getText().isEmpty() || !newPasswordField.getText().isEmpty()) {
                if (oldPasswordInput.getText().equals(DataBaseAccess.getUser().getPassword())) {
                    if (Validator.isPasswordValid(newPasswordField.getText())) {
                        if(!newPasswordField.getText().equals(repeatNewPasswordField.getText())) {
                            PageManager.showNotification("Пароли не совпадают!");
                        } else {
                            correctUpdateConditions = true;
                        }
                    }
                } else {
                    PageManager.showNotification("Неверный пароль!");
                }
            } else {
                correctUpdateConditions = true;
            }

            if (correctUpdateConditions) {
                DataBaseAccess.getUser().setTrainingLength(trainingLengthInput.getValueFactory().getValue());

                if (!newPasswordField.getText().isEmpty()) {
                    DataBaseAccess.getUser().setPassword(newPasswordField.getText());
                }

                PageManager.loadPage(new MainPage());
            }
        });

        HBox buttons = new HBox(exitButton, saveButton);
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        VPane vPane = new VPane(settingsIcon, trainingSettingsLabel, trainingLengthBox, new Separator(),
                protectionSettingsLabel, oldPasswordInput, newPasswordField, repeatNewPasswordField, buttons);
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
