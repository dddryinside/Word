package com.dddryinside.word.element;

import com.dddryinside.word.value.Language;
import com.dddryinside.word.value.TrainingType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class TrainingStart extends HBox {
    public TrainingStart() {
        ObservableList<TrainingType> trainingTypes = FXCollections.observableArrayList(TrainingType.values());
        ComboBox<TrainingType> trainingTypesComboBox = new ComboBox<>(trainingTypes);
        trainingTypesComboBox.setMinWidth(170);

        trainingTypesComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(TrainingType object) {
                return object != null ? object.getName() : "";
            }

            @Override
            public TrainingType fromString(String string) {
                return null;
            }
        });

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

        Button trainingButton = new Button("Вперёд!");

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(trainingTypesComboBox, languagesComboBox, trainingButton);
        this.setMaxWidth(500);
    }
}
