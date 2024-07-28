package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.HPane;
import com.dddryinside.word.model.User;
import com.dddryinside.word.service.DataBaseAccess;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Test implements Page {
    @Override
    public Scene getInterface() {
        User user = new User("Черников Илья", "dddryinside", "12345678", true);

        Label name = new Label(user.getName());
        name.getStyleClass().add("name-label");

        Label username = new Label("@" + user.getUsername());
        username.getStyleClass().add("username-label");

        Hyperlink editProfileButton = new Hyperlink("Редактировать");
        Hyperlink logOutButton = new Hyperlink("Выйти");
        logOutButton.setOnAction(event -> DataBaseAccess.logOut());

        HBox buttonsBox = new HBox(editProfileButton, logOutButton);
        buttonsBox.setSpacing(10);

        VBox info = new VBox(name, username, buttonsBox);

        HPane hPane = new HPane(getAvatar(), info);
        hPane.setWidth(500);
        hPane.setHeight(150);
        hPane.setShadow();
        hPane.setBorderRadius();
        hPane.setSpacing(30);
        hPane.setPadding(new Insets(20));

        HBox container = new HBox(hPane);
        container.setAlignment(Pos.CENTER);

        return new Scene(container);
    }

    private ImageView getAvatar() {
        Image image = new Image("avatar.png"); // Замените путь на путь к вашему изображению
        ImageView imageView = new ImageView(image);

        // Создание круга
        Circle circle = new Circle();
        circle.setRadius(50); // Установите радиус круга
        circle.setCenterX(50); // Центр круга по оси X
        circle.setCenterY(50); // Центр круга по оси Y

        // Установка маски
        imageView.setClip(circle);

        // Установка размеров ImageView, чтобы оно соответствовало размерам круга
        imageView.setFitWidth(100); // Установите ширину, равную диаметру круга
        imageView.setFitHeight(100); // Установите высоту, равную диаметру круга
        imageView.setPreserveRatio(true); // Сохранение пропорций изображения
        imageView.setSmooth(true); // Плавное отображение изображения

        return imageView;
    }
}
