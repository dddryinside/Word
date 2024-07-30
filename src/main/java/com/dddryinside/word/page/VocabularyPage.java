package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.ResourceLoader;
import com.dddryinside.word.value.Language;
import com.dddryinside.word.value.Status;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
        table.setMaxWidth(800);

        ObservableList<TableWord> data = getTableWordsFromDB();
        table.setItems(data);

        return table;
    }

    private HBox setupTopPanel() {
        MFXButton escapeButton = new MFXButton(null);
        escapeButton.setGraphic(ResourceLoader.loadIcon("bi-arrow-left"));
        escapeButton.setButtonType(ButtonType.FLAT);
        escapeButton.setOnAction(event -> PageManager.loadPage(new MainPage()));

        TextField searchField = new TextField();
        searchField.setMinWidth(250);
        searchField.setPromptText("Поиск");

        Button searchButton = new Button(null);
        searchButton.setGraphic(ResourceLoader.loadIcon("bi-search"));
        searchButton.setMinHeight(32);
        //searchButton.setOnAction(event -> PageManager.loadPage(new MainPage()));

        HBox topPanel = new HBox(escapeButton, setupPopupFilter(), searchField, searchButton);
        topPanel.setPadding(new Insets(10));
        topPanel.setSpacing(10);
        topPanel.setAlignment(Pos.CENTER);

        return topPanel;
    }

    private Button setupPopupFilter() {
        Button button = new Button("Фильтр");

        ObservableList<Language> languageValues = FXCollections.observableArrayList(Language.values());
        ComboBox<Language> languagesFilter = new ComboBox<>(languageValues);
        languagesFilter.setMinWidth(200);

        languagesFilter.setConverter(new StringConverter<>() {
            @Override
            public String toString(Language object) {
                return object != null ? object.getName() : "";
            }

            @Override
            public Language fromString(String string) {
                return null;
            }
        });

        ObservableList<Status> statusValues = FXCollections.observableArrayList(Status.values());
        ComboBox<Status> statusFilter = new ComboBox<>(statusValues);
        statusFilter.setMinWidth(200);

        statusFilter.setConverter(new StringConverter<>() {
            @Override
            public String toString(Status object) {
                return object != null ? object.getName() : "";
            }

            @Override
            public Status fromString(String string) {
                return null;
            }
        });


        HBox buttons = new HBox(20);
        Hyperlink cancelButton = new Hyperlink("Отмена");
        Hyperlink applyButton = new Hyperlink("Применить");
        buttons.getChildren().addAll(cancelButton, applyButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        // Создаем Stage для модального окна
        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(PageManager.getStage());

        VBox popupContent = new VBox(languagesFilter, statusFilter, buttons);
        popupContent.setStyle("-fx-background-color: white; -fx-border-color: #D6D6D6; -fx-border-radius: 5px; -fx-border-width: 1;");
        popupContent.setPadding(new Insets(20));
        popupContent.setSpacing(20);

        StackPane popupRoot = new StackPane(popupContent);
        popupRoot.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3);"); // Полупрозрачный фон

        Scene popupScene = new Scene(popupRoot);
        popupScene.getStylesheets().add("styles.css");
        popupStage.setScene(popupScene);

        button.setOnAction(event -> {
            Bounds bounds = button.localToScreen(button.getBoundsInLocal());
            popupStage.setX(bounds.getMinX());
            popupStage.setY(bounds.getMaxY() + 10);
            popupStage.show();
        });

        cancelButton.setOnAction(event -> {
            popupStage.hide();
        });

        return button;
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
