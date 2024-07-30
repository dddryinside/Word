package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.VPane;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.ResourceLoader;
import com.dddryinside.word.service.SettingAccess;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import org.kordamp.ikonli.javafx.FontIcon;

public class SettingsPage implements Page {
    @Override
    public Scene getInterface() {
        FontIcon settingsIcon = ResourceLoader.loadIcon("bi-gear", 35);
        VBox.setMargin(settingsIcon, new Insets(0, 0, 20, 0));

        Label trainingLengthLabel = new Label("Длинна тренировки (вопросов):");
        Spinner<Integer> trainingLengthInput = new Spinner<>();
        trainingLengthInput.setMinWidth(200);
        trainingLengthInput.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, SettingAccess.getTrainingLength()));

        Hyperlink exitButton = new Hyperlink("Отмена");
        exitButton.setOnAction(event -> PageManager.loadPage(new MainPage()));

        Hyperlink saveButton = new Hyperlink("Сохранить");
        saveButton.setOnAction(event -> {
            SettingAccess.setTrainingLength(trainingLengthInput.getValueFactory().getValue());
            PageManager.loadPage(new MainPage());
        });

        HBox buttons = new HBox(exitButton, saveButton);
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        VPane vPane = new VPane(settingsIcon, trainingLengthLabel, trainingLengthInput, buttons);
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
