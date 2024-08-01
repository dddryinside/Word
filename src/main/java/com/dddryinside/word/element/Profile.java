package com.dddryinside.word.element;

import com.dddryinside.word.model.User;
import com.dddryinside.word.service.DataBaseAccess;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;


public class Profile extends VBox {
    public Profile(User user) {
/*        Label nameLabel = new Label(user.getName());
        nameLabel.setWrapText(true);
        nameLabel.getStyleClass().add("name-label");*/

        Label username = new Label("@" + user.getUsername());
        username.getStyleClass().add("username-label");

        Hyperlink editProfileButton = new Hyperlink("Редактировать");
        Hyperlink logOutButton = new Hyperlink("Выйти");
        logOutButton.setOnAction(event -> DataBaseAccess.logOut());

        HBox buttonsBox = new HBox(editProfileButton, logOutButton);
        buttonsBox.setSpacing(10);



        this.getChildren().addAll(getAvatar(), username, buttonsBox);
        this.setMaxWidth(400);



/*        Background DEFAULT_BACKGROUND = new Background(new BackgroundFill(Color.LIGHTGRAY, null, null));
        this.setBackground(DEFAULT_BACKGROUND);*/
    }

    private StackPane getAvatar() {
        Image image = new Image("avatar_default.png"); // Замените путь на путь к вашему изображению
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

        // Добавление ImageView в сцену
        StackPane root = new StackPane(imageView);

        return root;
    }
}
