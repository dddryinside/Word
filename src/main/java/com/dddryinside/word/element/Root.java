package com.dddryinside.word.element;

import com.dddryinside.word.page.MainPage;
import com.dddryinside.word.page.VocabularyPage;
import com.dddryinside.word.service.PageManager;
import com.dddryinside.word.contract.Page;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Root extends BorderPane {
    public void setToCenter(Node... children) {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(children);

        super.setCenter(container);
    }

    public void setToTopCenter(Node... children) {
        VBox container = new VBox();
        VBox.setVgrow(container, Priority.ALWAYS);
        container.getChildren().addAll(children);

/*        Background DEFAULT_BACKGROUND = new Background(new BackgroundFill(Color.LIGHTGREEN, null, null));
        container.setBackground(DEFAULT_BACKGROUND);*/

        super.setCenter(container);
    }

    public void setMenuBar() {
        Menu menu = new Menu("Меню");

        MenuItem main = new MenuItem("Главная");
        main.setOnAction(event -> PageManager.loadPage(new MainPage()));
        MenuItem settings = new MenuItem("Настройки");
        //tests.setOnAction(event -> PageManager.loadPage(new SettingsPage()));

        MenuItem vocabulary = new MenuItem("Словарь");
        vocabulary.setOnAction(event -> PageManager.loadPage(new VocabularyPage()));
        menu.getItems().addAll(main, settings, vocabulary);

        Menu information = new Menu("Информация");

        MenuItem about = new MenuItem("О приложении");
        //about.setOnAction(event -> PageManager.loadPage(new AboutPage()));
        information.getItems().add(about);

        MenuBar menuBar = new MenuBar(menu, information);
        menuBar.setMinHeight(25);
        this.setTop(menuBar);
    }
}