package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.*;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.TrainingService;
import com.dddryinside.word.value.TrainingType;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class MainPage implements Page {
    @Override
    public Scene getInterface() {
        Profile profile = new Profile(DataBaseAccess.getUser());


        /*CreateWord createWord = new CreateWord();
        createWord.setMinWidth(400);*/

/*        SuperPanel panel = new SuperPanel("Добавьте слово", createWord);
        panel.setPrimaryStyle();*/

        JFXButton button = new JFXButton("Пройти тренировку");
        button.setOnAction(event -> {
            TrainingService.initializeTraining(TrainingType.LEARNING);
            if (TrainingService.getWords().size() < 10) {
                PageManager.showNotification("Для того, что бы начать тренировку в вашем словаре должно быть минимум 10 слов!");
            } else {
                TrainingService.iterate();
            }
        });

        TrainingStart trainingStart = new TrainingStart();

        VBox container = new VBox(profile, trainingStart, button);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        Root root = new Root();
        root.setMenuBar();
        root.setToCenter(container);

        return new Scene(root);
    }
}
