package com.dddryinside.word.element;

import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.TrainingService;
import com.dddryinside.word.value.Language;
import com.dddryinside.word.value.TrainingType;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;

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

        FontIcon goIcon = new FontIcon();
        goIcon.setIconSize(25);
        goIcon.setIconLiteral("bi-caret-right-square");
        goIcon.setIconColor(Paint.valueOf("GREEN"));

        HBox goButtonContainer = new HBox(goIcon);
        goButtonContainer.setAlignment(Pos.CENTER_LEFT);
        goButtonContainer.setSpacing(10);

        MFXButton goButton = new MFXButton(null);
        goButton.setGraphic(goButtonContainer);
        goButton.setButtonType(ButtonType.FLAT);

        goButton.setOnAction(event -> {
            TrainingService.initializeTraining(TrainingType.LEARNING);
            if (TrainingService.getWords().size() < 10) {
                PageManager.showNotification("Для того, что бы начать тренировку в вашем словаре должно быть минимум 10 слов!");
            } else {
                TrainingService.iterate();
            }
        });

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(trainingTypesComboBox, languagesComboBox, goButton);
        this.setMaxWidth(400);
    }
}
