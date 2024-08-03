package com.dddryinside.page;

import com.dddryinside.element.Profile;
import com.dddryinside.element.StatisticChart;
import com.dddryinside.contract.Page;
import com.dddryinside.element.VPane;
import com.dddryinside.service.PageManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

public class MainPage implements Page {
    @Override
    public Scene getInterface() {
        Profile profile = new Profile();

        HBox horizontalContainer = new HBox(profile, setupMenuButtons());
        horizontalContainer.setSpacing(20);

        VPane vPane = new VPane(horizontalContainer);
        vPane.setWidth(800);
        vPane.setShadow();
        vPane.setBorderRadius();
        vPane.setSpacing(20);
        vPane.setPadding(new Insets(20));
        vPane.setAlignment(Pos.CENTER);


        StatisticChart statisticChart = new StatisticChart();

        VBox container = new VBox(vPane, statisticChart);
        container.setSpacing(20);
        container.setAlignment(Pos.CENTER);

        return new Scene(container);
    }

    private VBox setupMenuButtons() {
        FontIcon newWordIcon = new FontIcon();
        newWordIcon.setIconSize(25);
        newWordIcon.setIconLiteral("bi-plus-circle");
        newWordIcon.setIconColor(Paint.valueOf("GREEN"));

        HBox newWordButtonContainer = new HBox(newWordIcon, new Label("Добавить слово"));
        newWordButtonContainer.setAlignment(Pos.CENTER_LEFT);
        newWordButtonContainer.setSpacing(20);

        Button newWordButton = new Button(null);
        newWordButton.setGraphic(newWordButtonContainer);
        newWordButton.setMinWidth(200);
        newWordButton.setMinHeight(40);
        newWordButton.setOnAction(event -> PageManager.loadPage(new NewWordPage()));



        FontIcon vocabularyIcon = new FontIcon();
        vocabularyIcon.setIconSize(25);
        vocabularyIcon.setIconLiteral("bi-book");

        HBox vocabularyButtonContainer = new HBox(vocabularyIcon, new Label("Словарь"));
        vocabularyButtonContainer.setAlignment(Pos.CENTER_LEFT);
        vocabularyButtonContainer.setSpacing(20);

        Button vocabularyButton = new Button(null);
        vocabularyButton.setGraphic(vocabularyButtonContainer);
        vocabularyButton.setMinWidth(200);
        vocabularyButton.setMinHeight(40);
        vocabularyButton.setOnAction(event -> PageManager.loadPage(new VocabularyPage(null, null)));



        FontIcon settingsIcon = new FontIcon();
        settingsIcon.setIconSize(25);
        settingsIcon.setIconLiteral("bi-gear");

        HBox settingsButtonContainer = new HBox(settingsIcon, new Label("Настройки"));
        settingsButtonContainer.setAlignment(Pos.CENTER_LEFT);
        settingsButtonContainer.setSpacing(20);

        Button settingsButton = new Button(null);
        settingsButton.setGraphic(settingsButtonContainer);
        settingsButton.setMinWidth(200);
        settingsButton.setMinHeight(40);
        settingsButton.setOnAction(event -> PageManager.loadPage(new SettingsPage()));



        FontIcon infoIcon = new FontIcon();
        infoIcon.setIconSize(25);
        infoIcon.setIconLiteral("bi-info-circle");

        HBox infoButtonContainer = new HBox(infoIcon, new Label("О приложении"));
        infoButtonContainer.setAlignment(Pos.CENTER_LEFT);
        infoButtonContainer.setSpacing(20);

        Button infoButton = new Button(null);
        infoButton.setGraphic(infoButtonContainer);
        infoButton.setMinWidth(200);
        infoButton.setMinHeight(40);
        infoButton.setOnAction(event -> PageManager.loadPage(new AboutPage()));


        VBox container = new VBox();
        container.getChildren().addAll(newWordButton, vocabularyButton, settingsButton, infoButton);

        return container;
    }
}
