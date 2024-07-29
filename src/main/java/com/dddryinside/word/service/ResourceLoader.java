package com.dddryinside.word.service;

import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

public class ResourceLoader {
    public static FontIcon loadIcon (String name) {
        FontIcon icon = new FontIcon();
        icon.setIconLiteral(name);
        return icon;
    }

    public static FontIcon loadIcon (String name, int size) {
        FontIcon icon = new FontIcon();
        icon.setIconLiteral(name);
        icon.setIconSize(size);
        return icon;
    }

    public static FontIcon loadIcon (String name, int size, Paint color) {
        FontIcon icon = new FontIcon();
        icon.setIconLiteral(name);
        icon.setIconSize(size);
        icon.setIconColor(color);
        return icon;
    }
}
