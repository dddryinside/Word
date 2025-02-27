package com.dddryinside.element;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VPane extends VBox {
    public VPane(Node... nodes) {
        super(nodes);
    }

    public void setShadow() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.LIGHTGRAY);
        dropShadow.setRadius(20);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);

        this.setEffect(dropShadow);
    }

    public void setBorderRadius() {
        this.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
    }

    public void setWidth(int width) {
        this.setMinWidth(width);
        this.setMaxWidth(width);
    }

    public void setHeight(int height) {
        this.setMinHeight(height);
        this.setMaxHeight(height);
    }
}
