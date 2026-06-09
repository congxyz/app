package com.xyzfood.Render.utils;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public final class ToastUtil {
    private static StackPane root;
    private static Label toastLabel;

    private ToastUtil() {
    }

    public static void install(StackPane appRoot) {
        root = appRoot;
        toastLabel = new Label();
        toastLabel.getStyleClass().add("toast");
        toastLabel.setMaxWidth(560);
        toastLabel.setWrapText(true);
        toastLabel.setTextAlignment(TextAlignment.CENTER);
        toastLabel.setMouseTransparent(true);
        toastLabel.setVisible(false);
        StackPane.setAlignment(toastLabel, Pos.CENTER);
        root.getChildren().add(toastLabel);
    }

    public static void show(String message) {
        if (toastLabel == null) {
            return;
        }
        toastLabel.setText(message);
        toastLabel.setVisible(true);
        toastLabel.toFront();

        FadeTransition fadeIn = new FadeTransition(Duration.millis(140), toastLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        PauseTransition wait = new PauseTransition(Duration.seconds(2.2));
        wait.setOnFinished(event -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(220), toastLabel);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(done -> toastLabel.setVisible(false));
            fadeOut.play();
        });
        wait.play();
    }
}

