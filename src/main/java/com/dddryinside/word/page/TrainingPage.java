package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.VPane;
import com.dddryinside.word.model.TrainingIteration;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.TrainingService;
import com.dddryinside.word.value.AppColor;
import com.jfoenix.controls.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TrainingPage implements Page {
    private final TrainingIteration training;

    public TrainingPage(TrainingIteration training) {
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

        Label trainingWord = new Label(training.getWord().getWord());
        trainingWord.setFont(Font.font(16));

        VBox content = new VBox(10);
        content.getChildren().addAll(trainingWord, optionsBox);



        CheckBox learnedCheckBox = new CheckBox("Я уже выучил это слово");
        learnedCheckBox.setSelected(TrainingService.isWordLeaned(training.getWord()));

        learnedCheckBox.setOnAction(event -> {
            if (learnedCheckBox.isSelected()) {
                TrainingService.addLearnedWord(training.getWord());
            } else {
                TrainingService.removeLearnedWord(training.getWord());
            }
        });

        HBox checkBoxContainer = new HBox(learnedCheckBox);
        checkBoxContainer.setAlignment(Pos.BASELINE_LEFT);
        VBox.setMargin(checkBoxContainer, new Insets(20, 0, 0, 0));



        Hyperlink continueButton;
        Hyperlink exitButton = new Hyperlink("Выйти");
        exitButton.setOnAction(event -> {
            if (PageManager.showConfirmation("Уверены, что ходите выйти?", "")) {
                TrainingService.stopTraining();
                PageManager.loadPage(new MainPage());
            }
        });

        if (training.getSize() != training.getIteration() + 1) {
            continueButton = new Hyperlink("Продолжить");
            continueButton.setOnAction(event -> {
                if (options.getSelectedToggle() != null) {
                    if (!options.getSelectedToggle().getUserData().equals(training.getWord().getTranslation())) {
                        PageManager.showNotification("Ошибка! " + training.getWord().getWord() + " - " + training.getWord().getTranslation());
                    } else {
                        TrainingService.iterate();
                    }
                }
            });
        } else {
            continueButton = new Hyperlink("Закончить");
            continueButton.setOnAction(event -> {
                TrainingService.stopTraining();
                DataBaseAccess.saveTrainingResult();
                PageManager.loadPage(new MainPage());
            });
        }

        HBox buttons = new HBox(exitButton, continueButton);
        buttons.setAlignment(Pos.BASELINE_RIGHT);
        buttons.setMaxWidth(400);
        buttons.setSpacing(20);

        Label trainingTitle = new Label(training.getTrainingType().getName());
        trainingTitle.setFont(Font.font(16));

        VPane vPane = new VPane(trainingTitle, content, checkBoxContainer, buttons);
        vPane.setSpacing(20);
        vPane.setShadow();
        vPane.setBorderRadius();
        vPane.setWidth(400);
        vPane.setAlignment(Pos.CENTER);
        vPane.setPadding(new Insets(20));

        VBox container = new VBox(progressBar, vPane);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        return new Scene(container);
    }

    private double getProgress() {
        double percentage = ((double) (training.getIteration() + 1) / training.getSize());
        return Math.round(percentage * 100.0) / 100.0;
    }
}
