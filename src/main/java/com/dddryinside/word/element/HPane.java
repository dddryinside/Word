package com.dddryinside.word.element;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HPane extends HBox {
    public HPane(Node... nodes) {
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
