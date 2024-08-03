package com.dddryinside.word.element;

import com.dddryinside.word.model.User;
import com.dddryinside.word.page.UpdateUserPage;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Profile extends VBox {
    public Profile() {
        HBox profilePanel = new HBox(getAvatar(), setupProfileInfo());
        profilePanel.setSpacing(30);

        StartTraining trainingStart = new StartTraining();

        this.getChildren().addAll(profilePanel, trainingStart);
        this.setSpacing(25);
    }

    private VBox setupProfileInfo() {
        User user = DataBaseAccess.getUser();

        Label name = new Label(user.getName());
        name.getStyleClass().add("name-label");
        name.setWrapText(true);

        Label username = new Label("@" + user.getUsername());
        username.getStyleClass().add("username-label");

        Hyperlink editProfileButton = new Hyperlink("Редактировать");
        editProfileButton.setOnAction(event -> PageManager.loadPage(new UpdateUserPage()));
        Hyperlink logOutButton = new Hyperlink("Выйти");
        logOutButton.setOnAction(event -> DataBaseAccess.logOut());

        HBox buttonsBox = new HBox(editProfileButton, logOutButton);
        buttonsBox.setSpacing(10);

        VBox container = new VBox(name, username, buttonsBox);
        container.setMinWidth(350);

        return container;
    }

    private ImageView getAvatar() {
        Image image = new Image(DataBaseAccess.getUser().getAvatar().getFile());
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
