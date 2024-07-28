package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.Root;
import com.dddryinside.word.element.SuperLabel;
import com.dddryinside.word.element.SuperPanel;
import com.dddryinside.word.model.Training;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.TrainingService;
import com.dddryinside.word.value.AppColor;
import com.jfoenix.controls.*;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TrainingPage implements Page {
    private final Training training;

    public TrainingPage(Training training) {
        this.training = training;
    }

    @Override
    public Scene getInterface() {
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setMinWidth(400);
        progressBar.setMaxHeight(5);
        progressBar.setProgress(getProgress());
        progressBar.getStyleClass().add("progress-bar-success");

        final ToggleGroup options = new ToggleGroup();

        JFXRadioButton option_1 = new JFXRadioButton(training.getOptions().get(0));
        option_1.setUserData(training.getOptions().get(0));
        option_1.setSelectedColor(AppColor.BLUE.getColor());
        option_1.setToggleGroup(options);

        JFXRadioButton option_2 = new JFXRadioButton(training.getOptions().get(1));
        option_2.setUserData(training.getOptions().get(1));
        option_2.setSelectedColor(AppColor.BLUE.getColor());
        option_2.setToggleGroup(options);

        JFXRadioButton option_3 = new JFXRadioButton(training.getOptions().get(2));
        option_3.setUserData(training.getOptions().get(2));
        option_3.setSelectedColor(AppColor.BLUE.getColor());
        option_3.setToggleGroup(options);

        JFXRadioButton option_4 = new JFXRadioButton(training.getOptions().get(3));
        option_4.setUserData(training.getOptions().get(3));
        option_4.setSelectedColor(AppColor.BLUE.getColor());
        option_4.setToggleGroup(options);

        VBox optionsBox = new VBox(option_1, option_2, option_3, option_4);
        VBox.setMargin(optionsBox, new Insets(10, 0, 0, 0));
        optionsBox.setSpacing(15);

        VBox content = new VBox(10);
        content.getChildren().addAll(new SuperLabel(training.getWord().getWord()), optionsBox);

        SuperPanel panel = new SuperPanel(training.getTrainingType().getName(), content);
        panel.setPrimaryStyle();

        JFXCheckBox learned = new JFXCheckBox();
        learned.setText("Больше не показывать это слово, я его уже выучил");

        Hyperlink continueButton;
        Hyperlink exitButton = new Hyperlink("Выйти");

        if (training.getSize() != training.getIteration() + 1) {
            continueButton = new Hyperlink("Продолжить");
            continueButton.setOnAction(event -> {
                if (!options.getSelectedToggle().getUserData().equals(training.getWord().getTranslation())) {
                    PageManager.showNotification("Ошибка! " + training.getWord().getWord() + " - " + training.getWord().getTranslation());
                } else {
                    TrainingService.iterate();
                }
            });
        } else {
            continueButton = new Hyperlink("Закончить");
            continueButton.setOnAction(event -> PageManager.loadPage(new MainPage()));
        }

        HBox buttons = new HBox(exitButton, continueButton);
        buttons.setAlignment(Pos.BASELINE_RIGHT);
        buttons.setMaxWidth(400);
        buttons.setSpacing(20);

        VBox container = new VBox(progressBar, panel, learned, buttons);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        Root root = new Root();
        root.setToCenter(container);
        root.setMenuBar();

        return new Scene(root);
    }

    private double getProgress() {
        double percentage = ((double) (training.getIteration() + 1) / training.getSize());
        return Math.round(percentage * 10.0) / 10.0;
    }
}
