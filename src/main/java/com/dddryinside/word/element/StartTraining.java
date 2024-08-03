package com.dddryinside.word.element;

import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.TrainingService;
import com.dddryinside.word.value.Language;
import com.dddryinside.word.value.TrainingType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;
import org.kordamp.ikonli.javafx.FontIcon;

public class StartTraining extends HBox {
    public StartTraining() {
        ObservableList<TrainingType> trainingTypes = FXCollections.observableArrayList(TrainingType.values());
        ComboBox<TrainingType> trainingTypesComboBox = new ComboBox<>(trainingTypes);
        trainingTypesComboBox.setMinWidth(230);
        trainingTypesComboBox.setValue(TrainingType.LEARNING);

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
        languagesComboBox.setMinWidth(230);

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
        languagesComboBox.setValue(DataBaseAccess.getUser().getLearningLanguage());

        FontIcon goIcon = new FontIcon();
        goIcon.setIconSize(25);
        goIcon.setIconLiteral("bi-caret-right-square");
        goIcon.setIconColor(Paint.valueOf("GREEN"));

        HBox goButtonContainer = new HBox(goIcon);
        goButtonContainer.setAlignment(Pos.CENTER_LEFT);
        goButtonContainer.setSpacing(10);

        Button goButton = new Button(null);
        goButton.setGraphic(goButtonContainer);

        goButton.setOnAction(event -> {
            TrainingType trainingType = trainingTypesComboBox.getValue();
            Language language = languagesComboBox.getValue();

            if (DataBaseAccess.getWordsAmount(trainingType, language) < 5) {
                PageManager.showNotification("Для того, что бы начать тренировку в вашем словаре должно быть не меньше 5 подходящих слов!");
            } else {
                TrainingService.startTraining(trainingTypesComboBox.getValue(), languagesComboBox.getValue());
            }
        });

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().addAll(trainingTypesComboBox, languagesComboBox, goButton);
        this.setMaxWidth(400);

    }
}
