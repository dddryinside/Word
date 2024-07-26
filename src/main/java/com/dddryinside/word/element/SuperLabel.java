package com.dddryinside.word.element;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class SuperLabel extends Label {
    public SuperLabel(String value) {
        super(value);
        this.setFont(Font.font(14));
        this.setPadding(Insets.EMPTY);
    }

    public SuperLabel() {
        this.setFont(Font.font(14));
        this.setPadding(Insets.EMPTY);
    }
}
