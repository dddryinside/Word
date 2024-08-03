package com.dddryinside.page;

import com.dddryinside.model.Word;
import com.dddryinside.service.DataBaseAccess;
import com.dddryinside.service.Validator;
import com.dddryinside.value.Language;
import com.dddryinside.contract.Page;
import com.dddryinside.element.VPane;
import com.dddryinside.service.PageManager;
import com.dddryinside.service.ResourceLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;

public class UpdateWordPage implements Page {
    private final Word word;

    public UpdateWordPage(Word word) {
        this.word = word;
    }
    @Override
    public Scene getInterface() {
        FontIcon editIcon = ResourceLoader.loadIcon("bi-pencil", 25);
        VBox.setMargin(editIcon, new Insets(0, 0, 20, 0));

        TextField wordField = new TextField();
        wordField.setPromptText("Слово");
        wordField.setText(word.getWord());
        wordField.setMaxWidth(250);

        TextField translationField = new TextField();
        translationField.setPromptText("Перевод");
        translationField.setText(word.getTranslation());
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
        languagesComboBox.setValue(word.getLanguage());



        CheckBox statusCheckBox = new CheckBox("Слово изучено");
        if (word.getStatus() == 0) {
            statusCheckBox.setSelected(false);
        } else {
            statusCheckBox.setSelected(true);
        }
        HBox statusCheckBoxContainer = new HBox(statusCheckBox);
        statusCheckBoxContainer.setAlignment(Pos.BASELINE_LEFT);


        HBox deleteButtonContent = new HBox(ResourceLoader.loadIcon("bi-trash", 20),
                new Label("Удалить слово"));
        deleteButtonContent.setAlignment(Pos.CENTER);
        deleteButtonContent.setSpacing(10);
        Button deleteButton = new Button();
        deleteButton.setMinWidth(250);
        deleteButton.setGraphic(deleteButtonContent);
        deleteButton.setOnAction(event -> {
            if (PageManager.showConfirmation("Удалить слово «" + word.getWord() + "»?", "Это действие невозможно будет отменить. " +
                    "Вы уверены, что хотите удалить это слово из своего словаря?")) {
                DataBaseAccess.deleteWord(word);
                PageManager.loadPage(new VocabularyPage(null, null));
            }
        });


        Hyperlink escapeButton = new Hyperlink("Отмена");
        escapeButton.setOnAction(event -> PageManager.loadPage(new VocabularyPage(null, null)));
        Hyperlink saveButton = new Hyperlink("Сохранить");
        saveButton.setOnAction(event -> {
            updateWord(wordField.getText(),
                    translationField.getText(), languagesComboBox.getValue(), statusCheckBox.isSelected());
            PageManager.loadPage(new VocabularyPage(null, null));
        });

        HBox buttons = new HBox(escapeButton, saveButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        VBox.setMargin(buttons, new Insets(20, 0, 0, 0));
        buttons.setSpacing(20);
        buttons.setMaxWidth(250);





        VPane vPane = new VPane(editIcon, wordField, translationField, languagesComboBox, statusCheckBoxContainer, deleteButton, buttons);
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

    private void updateWord(String word, String translation, Language language, boolean status) {
        if (Validator.isWordValid(word) && Validator.isTranslationValid(translation)) {
            Word updatedWord = new Word();
            updatedWord.setId(this.word.getId());
            updatedWord.setWord(word);
            updatedWord.setTranslation(translation);
            updatedWord.setLanguage(language);

            if (status) {
                updatedWord.setStatus(1);
            } else {
                updatedWord.setStatus(0);
            }

            DataBaseAccess.updateWord(updatedWord);
        }
    }
}
