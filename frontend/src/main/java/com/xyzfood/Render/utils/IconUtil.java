package com.xyzfood.Render.utils;

import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

public final class IconUtil {
    private IconUtil() {
    }

    public static FontIcon create(String iconLiteral, String color, int size) {
        FontIcon icon = new FontIcon(iconLiteral);
        icon.setIconColor(Color.web(color));
        icon.setIconSize(size);
        return icon;
    }
}
