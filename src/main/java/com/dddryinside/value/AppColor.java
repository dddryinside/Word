package com.dddryinside.value;

import javafx.scene.paint.Color;

public enum AppColor {
    BLUE(Color.rgb(0, 149, 200)),
    RED(Color.rgb(255, 60, 60));

    private final Color color;

    AppColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}