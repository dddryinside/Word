package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.VPane;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.ResourceLoader;
import com.dddryinside.word.service.Validator;
import com.dddryinside.word.value.Language;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;

public class NewWordPage implements Page {
    @Override
    public Scene getInterface() {
        FontIcon infoIcon = ResourceLoader.loadIcon("bi-plus-circle", 25);
        infoIcon.setIconColor(Paint.valueOf("GREEN"));
        VBox.setMargin(infoIcon, new Insets(0, 0, 20, 0));

        TextField wordField = new TextField();
        wordField.setPromptText("Слово");
        wordField.setMaxWidth(250);

        TextField translationField = new TextField();
        translationField.setPromptText("Перевод");
        translationField.setMaxWidth(250);

        ObservableList<Language> languages = FXCollections.observableArrayList(Language.values());
        ComboBox<Language> languagesComboBox = new ComboBox<>(languages);

        languagesComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Language object) {
                return object != null ? object.getName() : "";
            }

            @Override
            public Language fromString(String string) {
                return null;
            }
        });
        languagesComboBox.setMinWidth(250);
        languagesComboBox.setValue(DataBaseAccess.getUser().getLearningLanguage());

        Hyperlink escapeButton = new Hyperlink("Отмена");
        escapeButton.setOnAction(event -> PageManager.loadPage(new MainPage()));
        Hyperlink saveButton = new Hyperlink("Сохранить");
        saveButton.setOnAction(event -> {
            saveWord(wordField.getText(), translationField.getText(), languagesComboBox.getValue());
        });

        HBox buttons = new HBox(escapeButton, saveButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        VBox.setMargin(buttons, new Insets(20, 0, 0, 0));
        buttons.setSpacing(20);
        buttons.setMaxWidth(250);

        VPane vPane = new VPane(infoIcon, wordField, translationField, languagesComboBox, buttons);
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

    private void saveWord(String word, String translation, Language language) {
        if (Validator.isWordValid(word) && Validator.isTranslationValid(translation)) {
            DataBaseAccess.saveWord(new Word(DataBaseAccess.getUser(), word, translation, language, 0));
            PageManager.loadPage(new MainPage());
        }
    }
}
