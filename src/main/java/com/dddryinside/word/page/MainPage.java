package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.element.VPane;
import com.dddryinside.word.element.TrainingStart;
import com.dddryinside.word.model.User;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

public class MainPage implements Page {
    @Override
    public Scene getInterface() {
        HBox profilePanel = new HBox(getAvatar(), setupProfileInfo());
        profilePanel.setSpacing(30);

        TrainingStart trainingStart = new TrainingStart();
        VBox leftBox = new VBox(profilePanel, trainingStart);
        leftBox.setSpacing(35);

        HBox horizontalContainer = new HBox(leftBox, setupMenuButtons());
        horizontalContainer.setSpacing(30);

        VPane vPane = new VPane(horizontalContainer);
        vPane.setWidth(700);
        vPane.setHeight(250);
        vPane.setShadow();
        vPane.setBorderRadius();
        vPane.setSpacing(20);
        vPane.setPadding(new Insets(20));
        vPane.setAlignment(Pos.CENTER);

        VBox container = new VBox(vPane);
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

        MFXButton newWordButton = new MFXButton(null);
        newWordButton.setGraphic(newWordButtonContainer);
        newWordButton.setButtonType(ButtonType.FLAT);
        newWordButton.setMinWidth(200);
        newWordButton.setMinHeight(40);
        newWordButton.setOnAction(event -> PageManager.loadPage(new NewWordPage()));



        FontIcon vocabularyIcon = new FontIcon();
        vocabularyIcon.setIconSize(25);
        vocabularyIcon.setIconLiteral("bi-book");

        HBox vocabularyButtonContainer = new HBox(vocabularyIcon, new Label("Словарь"));
        vocabularyButtonContainer.setAlignment(Pos.CENTER_LEFT);
        vocabularyButtonContainer.setSpacing(20);

        MFXButton vocabularyButton = new MFXButton(null);
        vocabularyButton.setGraphic(vocabularyButtonContainer);
        vocabularyButton.setButtonType(ButtonType.FLAT);
        vocabularyButton.setMinWidth(200);
        vocabularyButton.setMinHeight(40);



        FontIcon settingsIcon = new FontIcon();
        settingsIcon.setIconSize(25);
        settingsIcon.setIconLiteral("bi-gear");

        HBox settingsButtonContainer = new HBox(settingsIcon, new Label("Настройки"));
        settingsButtonContainer.setAlignment(Pos.CENTER_LEFT);
        settingsButtonContainer.setSpacing(20);

        MFXButton settingsButton = new MFXButton(null);
        settingsButton.setGraphic(settingsButtonContainer);
        settingsButton.setButtonType(ButtonType.FLAT);
        settingsButton.setMinWidth(200);
        settingsButton.setMinHeight(40);



        FontIcon infoIcon = new FontIcon();
        infoIcon.setIconSize(25);
        infoIcon.setIconLiteral("bi-info-circle");

        HBox infoButtonContainer = new HBox(infoIcon, new Label("О приложении"));
        infoButtonContainer.setAlignment(Pos.CENTER_LEFT);
        infoButtonContainer.setSpacing(20);

        MFXButton infoButton = new MFXButton(null);
        infoButton.setGraphic(infoButtonContainer);
        infoButton.setButtonType(ButtonType.FLAT);
        infoButton.setMinWidth(200);
        infoButton.setMinHeight(40);


        VBox container = new VBox();
        container.getChildren().addAll(newWordButton, vocabularyButton, settingsButton, infoButton);

        return container;
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

        VBox container = new VBox(name, username, buttonsBox);
        container.setMinWidth(300);

        return container;
    }

    private ImageView getAvatar() {
        Image image = new Image("avatar.png");
        ImageView imageView = new ImageView(image);

        // Создание круга
        Circle circle = new Circle();
        circle.setRadius(50);
        circle.setCenterX(50);
        circle.setCenterY(50);

        // Установка маски
        imageView.setClip(circle);


        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        return imageView;
    }
}
