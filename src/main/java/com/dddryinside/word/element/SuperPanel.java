package com.dddryinside.word.element;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class SuperPanel extends Panel {
    public SuperPanel(String name, Node content) {
        super(name);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setCenter(content);

        this.setBody(content);
        this.setMaxWidth(400);
    }

    public void setPrimaryStyle() {
        this.getStyleClass().add("panel-primary");
    }
}
