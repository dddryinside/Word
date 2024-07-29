package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.ResourceLoader;
import com.dddryinside.word.value.Language;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import lombok.Getter;

import java.util.*;

public class VocabularyPage implements Page {
    @Override
    public Scene getInterface() {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(setupTopPanel());
        borderPane.setCenter(setupTable());
        borderPane.setBottom(setupBottomPanel());

        return new Scene(borderPane);
    }

    public HBox setupBottomPanel() {
        HBox deleteButtonContent = new HBox(ResourceLoader.loadIcon("bi-trash", 20),
                new Label("Удалить выбранное"));
        deleteButtonContent.setSpacing(10);
        Button deleteButton = new Button();
        deleteButton.setGraphic(deleteButtonContent);

        HBox editButtonContent = new HBox(ResourceLoader.loadIcon("bi-pencil", 20),
                new Label("Редактировать слово"));
        editButtonContent.setSpacing(10);
        Button editButton = new Button();
        editButton.setGraphic(editButtonContent);

        HBox bottomPanel = new HBox(editButton, deleteButton);
        bottomPanel.setPadding(new Insets(20));
        bottomPanel.setSpacing(10);

        bottomPanel.setAlignment(Pos.CENTER);

        return bottomPanel;
    }

    private TableView<TableWord> setupTable() {
        TableView<TableWord> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<TableWord, String> wordColumn = new TableColumn<>("Слово");
        wordColumn.setCellValueFactory(param -> param.getValue().getWord());

        TableColumn<TableWord, String> translationColumn = new TableColumn<>("Перевод");
        translationColumn.setCellValueFactory(param -> param.getValue().getTranslation());

        TableColumn<TableWord, String> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(param -> param.getValue().getStatus());

        TableColumn<TableWord, CheckBox> deleteColumn = new TableColumn<>("Удалить");
        deleteColumn.setCellValueFactory(param -> param.getValue().getDelete());

        table.getColumns().add(wordColumn);
        table.getColumns().add(translationColumn);
        table.getColumns().add(statusColumn);
        table.getColumns().add(deleteColumn);
        //table.getColumns().addAll(wordColumn, translationColumn, statusColumn, deleteColumn);
        table.setMaxWidth(810);

        ObservableList<TableWord> data = getTableWordsFromDB();
        table.setItems(data);

        return table;
    }

    private HBox setupTopPanel() {
        MFXButton escapeButton = new MFXButton(null);
        escapeButton.setGraphic(ResourceLoader.loadIcon("bi-arrow-left"));
        escapeButton.setButtonType(ButtonType.FLAT);
        escapeButton.setOnAction(event -> PageManager.loadPage(new MainPage()));

        ObservableList<Language> languages = FXCollections.observableArrayList(Language.values());
        ComboBox<Language> languagesComboBox = new ComboBox<>(languages);
        languagesComboBox.setMinWidth(170);

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

        TextField searchField = new TextField();
        searchField.setMinWidth(250);
        searchField.setPromptText("Поиск");

        Button searchButton = new Button(null);
        searchButton.setGraphic(ResourceLoader.loadIcon("bi-search"));
        searchButton.setMinHeight(32);
        //searchButton.setOnAction(event -> PageManager.loadPage(new MainPage()));

        HBox topPanel = new HBox(escapeButton, languagesComboBox, searchField, searchButton);
        topPanel.setPadding(new Insets(10));
        topPanel.setSpacing(10);
        topPanel.setAlignment(Pos.CENTER);

        return topPanel;
    }

    private ObservableList<TableWord> getTableWordsFromDB() {
        List<Word> words = DataBaseAccess.getWords();

        ObservableList<TableWord> tableWords = FXCollections.observableArrayList();
        for (Word word : words) {
            tableWords.add(new TableWord(word.getWord(), word.getTranslation()));
        }
        return tableWords;
    }


    @Getter
    public static class TableWord {
        private final StringProperty word;
        private final StringProperty translation;
        private final StringProperty status;
        private final SimpleObjectProperty<CheckBox> delete;

        public TableWord(String word, String translation) {
            this.word = new SimpleStringProperty(word);
            this.translation = new SimpleStringProperty(translation);
            this.status = new SimpleStringProperty("В изучении");

            CheckBox delete = new CheckBox();
            delete.getStyleClass().add("delete-check-box");
            this.delete = new SimpleObjectProperty<>(delete);
        }

        public ObservableValue<CheckBox> getDelete() {
            return delete;
        }
    }
}
