package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.Root;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.value.AppColor;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.enums.ButtonType;
import io.github.palexdev.materialfx.enums.FloatMode;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.*;

public class VocabularyPage implements Page {
    @Override
    public Scene getInterface() {
        MFXTextField search = new MFXTextField();
        search.setFloatMode(FloatMode.DISABLED);
        search.setMinWidth(250);
        search.setPromptText("Поиск");

        HBox topPanel = new HBox(search);
        topPanel.setPadding(new Insets(10));
        topPanel.setSpacing(10);
        topPanel.setMaxWidth(900);


        TableView<TableWord> table = new TableView<>();
        table.setMaxWidth(900);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<TableWord, String> wordColumn = new TableColumn<>("Слово");
        wordColumn.setCellValueFactory(param -> param.getValue().getWord());

        TableColumn<TableWord, String> translationColumn = new TableColumn<>("Перевод");
        translationColumn.setCellValueFactory(param -> param.getValue().getTranslation());

        TableColumn<TableWord, String> statusColumn = new TableColumn<>("Статус");
        statusColumn.setCellValueFactory(param -> param.getValue().getStatus());

        TableColumn<TableWord, JFXCheckBox> deleteColumn = new TableColumn<>("Удалить");
        deleteColumn.setCellValueFactory(param -> param.getValue().getDelete());

        table.getColumns().addAll(wordColumn, translationColumn, statusColumn, deleteColumn);

        ObservableList<TableWord> data = getTableWordsFromDB();
        table.setItems(data);

        MFXButton deleteButton = new MFXButton("Удалить");
        deleteButton.getStyleClass().add("delete-button");

        HBox bottomPanel = new HBox(deleteButton);
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.setSpacing(10);
        bottomPanel.setMaxWidth(900);

        bottomPanel.setAlignment(Pos.BASELINE_RIGHT);

        VBox container = new VBox(topPanel, table, bottomPanel);
        container.setSpacing(10);
        container.setAlignment(Pos.CENTER);

        Root rootPane = new Root();
        rootPane.setMenuBar();
        rootPane.setToCenter(container);

        return new Scene(rootPane);
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
        private final SimpleObjectProperty<JFXCheckBox> delete;

        public TableWord(String word, String translation) {
            this.word = new SimpleStringProperty(word);
            this.translation = new SimpleStringProperty(translation);
            this.status = new SimpleStringProperty("В изучении");

            JFXCheckBox delete = new JFXCheckBox();
            delete.setCheckedColor(AppColor.RED.getColor());
            this.delete = new SimpleObjectProperty<>(delete);
        }

        public ObservableValue<JFXCheckBox> getDelete() {
            return delete;
        }
    }
}
