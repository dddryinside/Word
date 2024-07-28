package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.VPane;
import com.dddryinside.word.element.TrainingStart;
import com.dddryinside.word.model.User;
import com.dddryinside.word.service.DataBaseAccess;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

public class Test implements Page {
    @Override
    public Scene getInterface() {
        HBox profilePanel = new HBox(getAvatar(), setupProfileInfo());
        profilePanel.setSpacing(30);

        VPane vPane = new VPane(profilePanel);
        vPane.setWidth(500);
        vPane.setHeight(200);
        vPane.setShadow();
        vPane.setBorderRadius();
        vPane.setSpacing(20);
        vPane.setPadding(new Insets(20));


        FontIcon newWordIcon = new FontIcon();
        newWordIcon.setIconSize(25);
        newWordIcon.setIconLiteral("bi-plus-circle");

        MFXButton newWordButton = new MFXButton("Добавить слово");
        newWordButton.setGraphic(newWordIcon);
        newWordButton.setButtonType(ButtonType.FLAT);


        FontIcon settingsIcon = new FontIcon();
        settingsIcon.setIconSize(25);
        settingsIcon.setIconLiteral("bi-gear");

        MFXButton settingsButton = new MFXButton("Настройки");
        settingsButton.setGraphic(settingsIcon);
        settingsButton.setButtonType(ButtonType.FLAT);


        FontIcon infoIcon = new FontIcon();
        infoIcon.setIconSize(25);
        infoIcon.setIconLiteral("bi-info-circle");

        MFXButton infoButton = new MFXButton("О приложении");
        infoButton.setGraphic(infoIcon);
        infoButton.setButtonType(ButtonType.FLAT);


        HBox iconsPane = new HBox(newWordButton, settingsButton, infoButton);
        vPane.getChildren().add(iconsPane);
        iconsPane.setSpacing(10);


        TrainingStart trainingStart = new TrainingStart();



        VBox container = new VBox(vPane, trainingStart);
        container.setSpacing(30);
        container.setAlignment(Pos.CENTER);

        return new Scene(container);
    }

    private VBox setupProfileInfo() {
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

        return new VBox(name, username, buttonsBox);
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
