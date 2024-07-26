package com.dddryinside.word.value;

import javafx.scene.paint.Color;

public enum AppColor {
    BLUE(Color.rgb(0, 149, 200));

    private final Color color;

    AppColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}