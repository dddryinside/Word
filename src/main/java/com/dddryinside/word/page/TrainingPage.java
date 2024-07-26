package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.SuperLabel;
import com.dddryinside.word.element.SuperPanel;
import com.dddryinside.word.model.Word;
import com.dddryinside.word.value.AppColor;
import com.dddryinside.word.value.TrainingType;
import com.jfoenix.controls.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class TrainingPage implements Page {
    private final Word word;
    private final TrainingType trainingType;
    public TrainingPage(Word word, TrainingType trainingType) {
        this.trainingType = trainingType;
        this.word = word;
    }

    @Override
    public Scene getInterface() {
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setMinWidth(400);
        progressBar.setMaxHeight(5);
        progressBar.setProgress(0.5);
        progressBar.getStyleClass().add("progress-bar-success");

        final ToggleGroup options = new ToggleGroup();

        JFXRadioButton option_1 = new JFXRadioButton("test");
        option_1.setSelectedColor(AppColor.BLUE.getColor());
        option_1.setToggleGroup(options);

        JFXRadioButton option_2 = new JFXRadioButton("test");
        option_2.setSelectedColor(AppColor.BLUE.getColor());
        option_2.setToggleGroup(options);

        JFXRadioButton option_3 = new JFXRadioButton("test");
        option_3.setSelectedColor(AppColor.BLUE.getColor());
        option_3.setToggleGroup(options);

        JFXRadioButton option_4 = new JFXRadioButton("test");
        option_4.setSelectedColor(AppColor.BLUE.getColor());
        option_4.setToggleGroup(options);

        VBox optionsBox = new VBox(option_1, option_2, option_3, option_4);
        VBox.setMargin(optionsBox, new Insets(10, 0, 0, 0));
        optionsBox.setSpacing(15);

        VBox content = new VBox(10);
        content.getChildren().addAll(new SuperLabel(word.getWord()), optionsBox);

        SuperPanel panel = new SuperPanel(trainingType.getName(), content);
        panel.setPrimaryStyle();

        Hyperlink exitButton = new Hyperlink("Выйти");
        Hyperlink continueButton = new Hyperlink("Продолжить");

        HBox buttons = new HBox(exitButton, continueButton);
        buttons.setAlignment(Pos.BASELINE_RIGHT);
        buttons.setMaxWidth(400);
        buttons.setSpacing(20);

        VBox container = new VBox(progressBar, panel, buttons);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        return new Scene(container);
    }
}
