package com.dddryinside.page;

import com.dddryinside.model.User;
import com.dddryinside.service.DataBaseAccess;
import com.dddryinside.service.Validator;
import com.dddryinside.value.Avatar;
import com.dddryinside.contract.Page;
import com.dddryinside.element.VPane;
import com.dddryinside.service.PageManager;
import com.dddryinside.service.ResourceLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.javafx.FontIcon;

public class UpdateUserPage implements Page {
    private Avatar avatar = DataBaseAccess.getUser().getAvatar();
    private final ImageView imageView = new ImageView();

    public UpdateUserPage() {
        setupAvatar(avatar);
    }

    @Override
    public Scene getInterface() {
        FontIcon personIcon = ResourceLoader.loadIcon("bi-person-fill", 35);
        VBox.setMargin(personIcon, new Insets(0, 0, 20, 0));

        TextField nameField = new TextField();
        nameField.setMinWidth(250);
        nameField.setText(DataBaseAccess.getUser().getName());
        nameField.setPromptText("фамилия и имя");

        Hyperlink escapeButton = new Hyperlink("Отмена");
        escapeButton.setOnAction(event -> PageManager.loadPage(new MainPage()));

        Hyperlink saveButton = new Hyperlink("Сохранить");
        saveButton.setOnAction(event -> {
            if (Validator.isNameValid(nameField.getText())) {
                User user = DataBaseAccess.getUser();
                user.setName(nameField.getText());
                user.setAvatar(avatar);

                DataBaseAccess.setUser(user);
                PageManager.loadPage(new MainPage());
            }
        });

        HBox buttons = new HBox(escapeButton, saveButton);
        VBox.setMargin(buttons, new Insets(20, 0, 0, 0));
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setMaxWidth(250);
        buttons.setSpacing(20);

        VPane vPane = new VPane(personIcon, nameField, setupAvatarChoice(), buttons);
        vPane.setSpacing(20);
        vPane.setShadow();
        vPane.setBorderRadius();
        vPane.setAlignment(Pos.CENTER);
        vPane.setPadding(new Insets(20));

        GridPane container = new GridPane();
        container.getChildren().add(vPane);
        container.setAlignment(Pos.CENTER);

        return new Scene(container);
    }

    private Node setupAvatarChoice() {
        HBox prevButtonContent = new HBox(ResourceLoader.loadIcon("bi-arrow-left-circle", 20));
        prevButtonContent.setSpacing(10);
        Button prevButton = new Button();
        prevButton.setGraphic(prevButtonContent);

        prevButton.setOnAction(event -> {
            avatar = getPrevAvatar(avatar);
            setupAvatar(avatar);
        });

        HBox nextButtonContent = new HBox(ResourceLoader.loadIcon("bi-arrow-right-circle", 20));
        nextButtonContent.setSpacing(10);
        Button nextButton = new Button();
        nextButton.setGraphic(nextButtonContent);

        nextButton.setOnAction(event -> {
            avatar = getNextAvatar(avatar);
            setupAvatar(avatar);
        });

        HBox container = new HBox(prevButton, imageView, nextButton);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        return container;
    }

    private void setupAvatar(Avatar avatar) {
        Image image = new Image(avatar.getFile());

        imageView.setImage(image);

        Circle circle = new Circle();
        circle.setRadius(50);
        circle.setCenterX(50);
        circle.setCenterY(50);

        imageView.setClip(circle);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
    }

    public static Avatar getNextAvatar(Avatar currentAvatar) {
        Avatar[] values = Avatar.values();
        int currentIndex = -1;

        for (int i = 0; i < values.length; i++) {
            if (values[i] == currentAvatar) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == values.length - 1) {
            return values[0];
        }

        return values[currentIndex + 1];
    }

    public static Avatar getPrevAvatar(Avatar currentAvatar) {
        Avatar[] values = Avatar.values();
        int currentIndex = -1;

        for (int i = 0; i < values.length; i++) {
            if (values[i] == currentAvatar) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == 0) {
            return values[values.length - 1];
        }

        return values[currentIndex - 1];
    }
}
