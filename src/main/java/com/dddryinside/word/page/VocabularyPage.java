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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import lombok.Getter;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.*;

public class VocabularyPage implements Page {

    @Override
    public Scene getInterface() {
        BorderPane borderPane = new BorderPane();

        ObservableList<TableWord> data = getTableWordsFromDB();
        if (!data.isEmpty()) {
            borderPane.setTop(setupTopPanel());
            borderPane.setCenter(setupTable(data));
            borderPane.setBottom(setupBottomPanel());
        } else {
            Label emptyDataBaseMessage = new Label("В вашем словаре пусто. ");
            emptyDataBaseMessage.setFont(Font.font(14));

            Hyperlink escapeButton = new Hyperlink("Назад");
            escapeButton.setOnAction(event -> PageManager.loadPage(new MainPage()));

            HBox content = new HBox(emptyDataBaseMessage, escapeButton);
            content.setAlignment(Pos.CENTER);

            borderPane.setCenter(content);
        }

        return new Scene(borderPane);
    }

    public HBox setupBottomPanel() {
        HBox prevButtonContent = new HBox(ResourceLoader.loadIcon("bi-arrow-left-circle", 20));
        prevButtonContent.setSpacing(10);
        Button prevButton = new Button();
        prevButton.setGraphic(prevButtonContent);

        HBox nextButtonContent = new HBox(ResourceLoader.loadIcon("bi-arrow-right-circle", 20));
        nextButtonContent.setSpacing(10);
        Button nextButton = new Button();
        nextButton.setGraphic(nextButtonContent);

        Label pageNumberLabel = new Label("1");
        pageNumberLabel.setFont(Font.font(14));

        HBox bottomPanel = new HBox(prevButton, pageNumberLabel, nextButton);
        bottomPanel.setPadding(new Insets(20));
        bottomPanel.setSpacing(20);

        bottomPanel.setAlignment(Pos.CENTER);

        return bottomPanel;
    }

    private TableView<TableWord> setupTable(ObservableList<TableWord> data) {
        TableView<TableWord> table = new TableView<>();

        TableColumn<TableWord, String> wordColumn = new TableColumn<>("Слово");
        wordColumn.setCellValueFactory(param -> param.getValue().getWord());
        wordColumn.setMinWidth(250);
        wordColumn.setMaxWidth(250);

        TableColumn<TableWord, String> translationColumn = new TableColumn<>("Перевод");
        translationColumn.setCellValueFactory(param -> param.getValue().getTranslation());
        translationColumn.setMinWidth(250);
        translationColumn.setMaxWidth(250);

        TableColumn<TableWord, String> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(param -> param.getValue().getStatus());
        statusColumn.setMinWidth(250);
        statusColumn.setMaxWidth(250);

        TableColumn<TableWord, Button> editColumn = new TableColumn<>();
        editColumn.setCellValueFactory(param -> param.getValue().getEditButton());
        editColumn.setMinWidth(50);
        editColumn.setMaxWidth(50);

        table.getColumns().add(wordColumn);
        table.getColumns().add(translationColumn);
        table.getColumns().add(statusColumn);
        table.getColumns().add(editColumn);
        table.setMaxWidth(800);

        table.setItems(data);

/*        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedWordProperty.set(newValue);
        });*/

        return table;
    }

    private HBox setupTopPanel() {
        Button escapeButton = new Button();
        escapeButton.setGraphic(ResourceLoader.loadIcon("bi-back", 20));
        escapeButton.setOnAction(event -> PageManager.loadPage(new MainPage()));

        TextField searchField = new TextField();
        searchField.setMinWidth(250);
        searchField.setPromptText("Поиск");

        Button searchButton = new Button();
        searchButton.setGraphic(ResourceLoader.loadIcon("bi-search", 20));
        //searchButton.setOnAction(event -> PageManager.loadPage(new MainPage()));

        HBox topPanel = new HBox(escapeButton, searchField, searchButton, setupPopupFilter());
        topPanel.setPadding(new Insets(10, 0, 10, 0));
        topPanel.setSpacing(10);
        topPanel.setMinWidth(800);
        topPanel.setAlignment(Pos.BASELINE_LEFT);

        HBox container = new HBox(topPanel);
        container.setAlignment(Pos.CENTER);

        return container;
    }

    private Button setupPopupFilter() {
        Button button = new Button();
        FontIcon filterButtonIcon = ResourceLoader.loadIcon("bi-filter" ,20);
        button.setGraphic(filterButtonIcon);

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
            popupStage.setX(bounds.getMinX() - 100);
            popupStage.setY(bounds.getMaxY() + 10);
            popupStage.show();
        });

        cancelButton.setOnAction(event -> popupStage.hide());

        return button;
    }

    private ObservableList<TableWord> getTableWordsFromDB() {
        List<Word> words = DataBaseAccess.getWords();

        ObservableList<TableWord> tableWords = FXCollections.observableArrayList();
        for (Word word : words) {
            tableWords.add(new TableWord(word.getWord(), word.getTranslation(), word.getStatus(), word));
        }
        return tableWords;
    }


    @Getter
    public static class TableWord {
        private final StringProperty word;
        private final StringProperty translation;
        private final StringProperty status;
        private final SimpleObjectProperty<Button> edit;
        private final Word wordObject;

        public TableWord(String word, String translation, int status, Word wordObject) {
            this.word = new SimpleStringProperty(word);
            this.translation = new SimpleStringProperty(translation);

            if (status == 0) {
                this.status = new SimpleStringProperty(Status.LEARN.getName());
            } else {
                this.status = new SimpleStringProperty(Status.LEARNED.getName());
            }

            this.wordObject = wordObject;

            Button edit = new Button();
            edit.setOnAction(event -> PageManager.loadPage(new UpdateWordPage(wordObject)));
            edit.setGraphic(ResourceLoader.loadIcon("bi-pencil"));
            this.edit = new SimpleObjectProperty<>(edit);
        }

        public ObservableValue<Button> getEditButton() {
            return edit;
        }
    }
}
