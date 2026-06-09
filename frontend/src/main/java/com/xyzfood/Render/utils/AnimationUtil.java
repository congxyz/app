package com.xyzfood.Render.utils;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public final class AnimationUtil {
    private AnimationUtil() {
    }

    public static void fadeIn(Node node) {
        FadeTransition transition = new FadeTransition(Duration.millis(180), node);
        transition.setFromValue(0.25);
        transition.setToValue(1);
        transition.play();
    }

    public static void addPressEffect(Node node) {
        node.setOnMousePressed(event -> scale(node, 0.97));
        node.setOnMouseReleased(event -> scale(node, 1));
    }

    private static void scale(Node node, double value) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(90), node);
        transition.setToX(value);
        transition.setToY(value);
        transition.play();
    }
}

