package com.xyzfood.Render.controllers.components;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.xyzfood.Render.utils.AppExecutor;
import com.xyzfood.Render.config.AppConfig;

public class AIChatController {
    
    @FXML private VBox chatContainer;
    @FXML private TextField messageField;
    @FXML private ScrollPane scrollPane;

    private void addUserMessage(String message) {

        Label label = new Label(message);

        label.setWrapText(true);

        label.getStyleClass().add("user-message");

        HBox box = new HBox(label);

        box.setAlignment(Pos.CENTER_RIGHT);

        chatContainer.getChildren().add(box);
    }

    private void addAIMessage(String message) {

        Label label = new Label(message);

        label.setWrapText(true);

        label.getStyleClass().add("ai-message");

        HBox box = new HBox(label);

        box.setAlignment(Pos.CENTER_LEFT);

        chatContainer.getChildren().add(box);
    }

    @FXML
    private void sendMessage() {

        String question = messageField.getText();

        if(question.isBlank()) {
            return;
        }

        addUserMessage(question);

        messageField.clear();

        AppExecutor.getExecutor().submit(() -> {

            String answer = AppConfig.getInstance().getXYZAIService().ask(question);

            Platform.runLater(() -> {

                addAIMessage(answer);

                scrollPane.setVvalue(1.0);
            });

        });
    }
}
