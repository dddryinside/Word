package com.dddryinside.word;

import com.dddryinside.word.mfx.JavaFXThemes;
import com.dddryinside.word.mfx.MaterialFXStylesheets;
import com.dddryinside.word.page.Test;
import com.dddryinside.word.service.DataBaseAccess;
import com.dddryinside.word.service.PageManager;

import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Word extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Word");
        Image icon = new Image("icon.png");
        stage.getIcons().add(icon);
        PageManager.setStage(stage);
        PageManager.setWindowSize(600, 900);

        PageManager.loadPage(new Test());

        /*UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA) // Optional if you don't need JavaFX's default theme, still recommended though
                .themes(MaterialFXStylesheets.forAssemble(true)) // Adds the MaterialFX's default theme. The boolean argument is to include legacy controls
                .setDeploy(true) // Whether to deploy each theme's assets on a temporary dir on the disk
                .setResolveAssets(true)
                .build()
                .setGlobal();

        DataBaseAccess.findAuthorisedUser();

        stage.setOnCloseRequest(event ->  {
            DataBaseAccess.updateUser();
            Platform.exit();
        });*/
    }

    public static void main(String[] args) {
        launch();
    }
}