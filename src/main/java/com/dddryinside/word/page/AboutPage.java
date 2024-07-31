package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.VPane;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.service.ResourceLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutPage implements Page {
    @Override
    public Scene getInterface() {
        ImageView appIcon = new ImageView(new Image("language.png"));
        appIcon.setPreserveRatio(true);
        appIcon.setFitWidth(100);


        Label text = new Label("Word - это приложение, которое сделает изучение новых слов простым " +
                "и увлекательным. Оно предназначено для более продвинутых учеников и не станет предлагать вам заготовленные " +
                "наборы слов. Вместо этого вы сами формируете свой словарь из тех слов, которые вам нужны. " +
                "Установите в настройках удобный для вас размер тренировок и проходите их " +
                "в любом количестве и когда угодно, отмечайте уже выученные слова, добавляйте новые. Вперёд!");
        text.setWrapText(true);
        text.setFont(Font.font("System", 14));


        FontIcon gitHubIcon = ResourceLoader.loadIcon("bi-github" ,25);
        Hyperlink gitHubLink = new Hyperlink("GitHub");
        gitHubLink.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/dddryinside/Word"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        HBox gitHubContainer = new HBox(gitHubIcon, gitHubLink);
        gitHubContainer.setAlignment(Pos.CENTER_LEFT);
        gitHubContainer.setSpacing(10);


        Hyperlink clearButton = new Hyperlink("Понятно");
        clearButton.setOnAction(event -> PageManager.loadPage(new MainPage()));
        HBox buttonContainer = new HBox(clearButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);


        VBox rightContainer = new VBox(text, gitHubContainer, buttonContainer);
        rightContainer.setSpacing(10);


        HBox container = new HBox(20);
        container.getChildren().addAll(appIcon, rightContainer);


        VPane vPane = new VPane(container);
        vPane.setWidth(500);
        vPane.setShadow();
        vPane.setBorderRadius();
        vPane.setAlignment(Pos.CENTER);
        vPane.setPadding(new Insets(20));

        GridPane gridPane = new GridPane();
        gridPane.getChildren().add(vPane);
        gridPane.setAlignment(Pos.CENTER);

        return new Scene(gridPane);
    }
}
