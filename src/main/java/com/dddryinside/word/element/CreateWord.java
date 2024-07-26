package com.dddryinside.word.element;

import com.dddryinside.word.model.Word;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.dataBase.WordDB;
import com.dddryinside.word.value.AppColor;
import com.dddryinside.word.value.Language;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class CreateWord extends VBox {
    public CreateWord() {
        JFXTextField wordField = new JFXTextField();
        wordField.setPromptText("слово");
        wordField.setFocusColor(AppColor.BLUE.getColor());

        JFXTextField translationField = new JFXTextField();
        translationField.setPromptText("перевод");
        translationField.setFocusColor(AppColor.BLUE.getColor());

        JFXComboBox<Language> languagesBox = new JFXComboBox<>();
        languagesBox.getItems().addAll(Language.values());
        languagesBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Language language) {
                return language != null ? language.getName() : "";
            }

            @Override
            public Language fromString(String string) {
                return null;
            }
        });
        languagesBox.setMinWidth(250);
        languagesBox.setFocusColor(AppColor.BLUE.getColor());
        languagesBox.setValue(Language.EN);

        Hyperlink saveButton = new Hyperlink("Сохранить");
        saveButton.setOnAction(event -> {
            if (stringIsEmpty(wordField.getText())) {
                PageManager.showNotification("Кажется, вы забыли ввести слово!");
            } else if (stringIsEmpty(translationField.getText())) {
                PageManager.showNotification("Кажется, вы забыли ввести перевод!");
            } else {
                WordDB.saveWord(new Word(DataBaseAccess.getUser(),
                        wordField.getText(), translationField.getText(), languagesBox.getValue(), 0));

                wordField.setText(null);
                translationField.setText(null);
            }
        });
        HBox buttons = new HBox(saveButton);
        buttons.setSpacing(20);
        buttons.setMaxWidth(250);

        this.getChildren().addAll(wordField, translationField, languagesBox, buttons);
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(250);
    }

    private boolean stringIsEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }
}
