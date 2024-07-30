package com.dddryinside.word.page;

import com.dddryinside.word.contract.Page;
import com.dddryinside.word.service.PageManager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;


public class Test implements Page {
    @Override
    public Scene getInterface() {
        // Основной контент
        Button button = new Button("Открыть всплывающую панель");
        StackPane root = new StackPane(button);

        // Всплывающая панель
        Popup popup = new Popup();
        VBox popupContent = new VBox(new Label("Это всплывающая панель"));


        popup.getContent().add(popupContent);

        // Обработчик нажатия на кнопку
        button.setOnAction(event -> {
            popup.show(PageManager.getStage());
        });

        return new Scene(root);
    }
}
