package com.dddryinside.word.element;

import com.dddryinside.word.model.User;
import com.dddryinside.word.service.DataBaseAccess;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Profile extends VBox {
    public Profile(User user) {
        Label nameLabel = new Label(user.getName());
        nameLabel.setWrapText(true);
        nameLabel.getStyleClass().add("name-label");

        Label username = new Label("@" + user.getUsername());
        username.getStyleClass().add("username-label");

        Hyperlink editProfileButton = new Hyperlink("Редактировать");
        Hyperlink logOutButton = new Hyperlink("Выйти");
        logOutButton.setOnAction(event -> DataBaseAccess.logOut());

        HBox buttonsBox = new HBox(editProfileButton, logOutButton);
        buttonsBox.setSpacing(10);

        this.getChildren().addAll(nameLabel, username, buttonsBox);
        this.setMaxWidth(400);

/*        Background DEFAULT_BACKGROUND = new Background(new BackgroundFill(Color.LIGHTGRAY, null, null));
        this.setBackground(DEFAULT_BACKGROUND);*/
    }
}
