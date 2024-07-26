package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.CreateWord;
import com.dddryinside.word.element.Profile;
import com.dddryinside.word.element.Root;
import com.dddryinside.word.service.DataBaseAccess;
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
        CreateWord createWord = new CreateWord();
        Profile profile = new Profile(DataBaseAccess.getUser());

        Panel panel = new Panel("Добавьте слово");
        panel.getStyleClass().add("panel-default");

        BorderPane content = new BorderPane();
        content.setPadding(new Insets(20));
        content.setCenter(createWord);
        panel.setBody(content);
        panel.setMaxWidth(400);

        JFXButton button = new JFXButton("Пройти тренировку");
        button.setOnAction(event -> {
            TrainingService trainingService = new TrainingService(TrainingType.LEARNING);
            trainingService.iterate();
        });

        VBox container = new VBox(profile, panel, button);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        Root root = new Root();
        root.setMenuBar();
        root.setToCenter(container);

        return new Scene(root);
    }
}
