package com.dddryinside.service;

import com.dddryinside.contract.Page;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.ikonli.javafx.FontIcon;

public class PageManager {
    private static Stage stage;

    public static void setStage(Stage stage) {
        PageManager.stage = stage;
    }

    public static Stage getStage() {
        return PageManager.stage;
    }

    public static void loadPage(Page page) {
        Scene scene = page.getInterface();
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.show();
    }

    public static void setWindowSize(int height, int width) {
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        stage.setHeight(height);
        stage.setWidth(width);
    }

    public static void showNotification(String message) {
        Stage notificationStage = new Stage();
        notificationStage.initModality(Modality.APPLICATION_MODAL);
        notificationStage.setResizable(false);
        notificationStage.setMinWidth(470);
        notificationStage.setMaxWidth(470);
        notificationStage.setMinHeight(170);
        notificationStage.setMaxHeight(170);

        notificationStage.setTitle("Сообщение");
        Image icon = new Image("icon.png");
        notificationStage.getIcons().add(icon);

        FontIcon infoIcon = ResourceLoader.loadIcon("bi-chat-text", 50);
        infoIcon.setIconColor(Paint.valueOf("GREY"));
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setFont(Font.font(14));

        HBox infoContainer = new HBox(infoIcon, messageLabel);
        infoContainer.setSpacing(20);

        Hyperlink closeButton = new Hyperlink("Понятно");
        closeButton.setOnAction(event -> notificationStage.close());
        HBox buttonContainer = new HBox(closeButton);
        buttonContainer.setAlignment(Pos.BASELINE_RIGHT);

        BorderPane root = new BorderPane();
        root.setCenter(infoContainer);
        root.setBottom(buttonContainer);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles.css");
        notificationStage.setScene(scene);
        notificationStage.show();
    }

    public static boolean showConfirmation(String message, String description) {
        BooleanProperty result = new SimpleBooleanProperty(false);

        // Создание и настройка окна уведомления
        Stage notificationStage = new Stage();
        notificationStage.initModality(Modality.APPLICATION_MODAL);
        notificationStage.setResizable(false);
        notificationStage.setWidth(470);
        notificationStage.setMinHeight(170);
        notificationStage.setTitle("Подтверждение");
        notificationStage.getIcons().add(new Image("icon.png"));

        // Создание и настройка меток сообщения и описания
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setFont(Font.font(14));

        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setFont(Font.font(14));

        // Контейнер для меток сообщения и описания
        VBox messageContainer = new VBox(10, messageLabel, descriptionLabel);
        messageContainer.setMinHeight(100);

        // Иконка информации
        FontIcon infoIcon = ResourceLoader.loadIcon("bi-question-circle", 50);
        infoIcon.setIconColor(Paint.valueOf("GREY"));

        // Контейнер для иконки и меток
        HBox infoContainer = new HBox(20, infoIcon, messageContainer);

        // Кнопки подтверждения и отмены
        Hyperlink closeButton = new Hyperlink("Отмена");
        closeButton.setOnAction(event -> {
            result.set(false);
            notificationStage.close();
        });

        Hyperlink okButton = new Hyperlink("Подтвердить");
        okButton.setOnAction(event -> {
            result.set(true);
            notificationStage.close();
        });

        // Контейнер для кнопок
        HBox buttonsContainer = new HBox(20, closeButton, okButton);
        buttonsContainer.setAlignment(Pos.BASELINE_RIGHT);

        // Основной контейнер и настройки сцены
        BorderPane root = new BorderPane();
        root.setCenter(infoContainer);
        root.setBottom(buttonsContainer);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles.css");
        notificationStage.setScene(scene);
        notificationStage.showAndWait();

        return result.get();
    }
}
