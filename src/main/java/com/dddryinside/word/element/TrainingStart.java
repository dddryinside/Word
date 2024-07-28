package com.dddryinside.word.element;

import com.dddryinside.word.value.Language;
import com.dddryinside.word.value.TrainingType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class TrainingStart extends VBox {
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
        languagesComboBox.setMinWidth(160);

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

        HBox panel = new HBox(trainingTypesComboBox, languagesComboBox, trainingButton);
        panel.setSpacing(10);

        this.getChildren().add(panel);
    }
}
