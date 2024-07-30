package com.dddryinside.word.service;

import com.dddryinside.word.contract.Page;
import com.jfoenix.controls.JFXAlert;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Locked;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Optional;

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
        Image icon = new Image("language.png");
        notificationStage.getIcons().add(icon);

        FontIcon infoIcon = ResourceLoader.loadIcon("bi-info-circle", 50);
        infoIcon.setIconColor(Paint.valueOf("#0095C8"));
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(message);
        alert.setContentText(description);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("icon.png"));

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
