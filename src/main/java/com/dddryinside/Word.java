package com.dddryinside;

import com.dddryinside.service.DataBaseAccess;
import com.dddryinside.service.PageManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Word extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Word");
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        PageManager.setStage(stage);
        PageManager.setWindowSize(600, 900);

        DataBaseAccess.findAuthorisedUser();

        stage.setOnCloseRequest(event ->  {
            DataBaseAccess.finishWork();
            Platform.exit();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}