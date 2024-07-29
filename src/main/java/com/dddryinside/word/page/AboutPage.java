package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.VPane;
import com.dddryinside.word.service.PageManager;
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
import javafx.scene.text.Font;

public class AboutPage implements Page {
    @Override
    public Scene getInterface() {
        Image icon = new Image("language.png");
        ImageView imageView = new ImageView(icon);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(100);

        Label text = new Label("Word - это приложение, которое сделает изучение новых слов простым " +
                "и увлекательным. Оно предназначено для более продвинутых учеников и не станет предлагать вам заготовленные " +
                "наборы слов. Вместо этого вы сами формируете свой словарь из тех слов, которые вам нужны. " +
                "Установите в настройках удобный для вас размер тренировок и проходите их " +
                "в любом количестве и когда угодно, отмечайте уже выученные слова, добавляйте новые. Вперёд!\n\nВерсия 1.0");
        text.setWrapText(true);
        text.setFont(Font.font("System", 14));

        HBox infoContainer = new HBox(20);
        infoContainer.getChildren().addAll(imageView, text);

        Hyperlink clearButton = new Hyperlink("Понятно");
        clearButton.setOnAction(event -> PageManager.loadPage(new MainPage()));
        HBox buttonContainer = new HBox(clearButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);

        VPane vPane = new VPane(infoContainer, buttonContainer);
        vPane.setWidth(500);
        vPane.setShadow();
        vPane.setBorderRadius();
        vPane.setAlignment(Pos.CENTER);
        vPane.setPadding(new Insets(20));

        GridPane container = new GridPane();
        container.getChildren().add(vPane);
        container.setAlignment(Pos.CENTER);

        return new Scene(container);
    }
}
