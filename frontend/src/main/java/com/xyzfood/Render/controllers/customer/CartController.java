package com.xyzfood.Render.controllers.customer;

import com.xyzfood.Render.App;
import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.models.Food;
import com.xyzfood.Render.utils.IconUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.application.Platform;
import com.xyzfood.Render.utils.AppExecutor;
import javafx.event.ActionEvent;

public class CartController {
    @FXML private TilePane foodGrid;

    @FXML
    private void initialize() {
        renderFoods();
    }

    private void renderFoods() {
        AppExecutor.getExecutor().submit(() -> {
                var foods = AppConfig.getInstance().getFoodService().getFoods();
                Platform.runLater(() -> {
                    foodGrid.getChildren().clear();
                    for (Food food : foods) {
                        if(!food.getDelete()){
                        foodGrid.getChildren().add(createFoodCard(food, false));
                        }
                    }});
        });
    }

    static VBox createFoodCard(Food food, boolean featured) {
        VBox card = new VBox();
        card.getStyleClass().add("food-card");

        Image image = new Image(food.getImage_path(),250,180,true,true,true);
        ImageView imageView = new ImageView(image);
        VBox imageBox = new VBox(imageView);
        imageBox.getStyleClass().add("food-image-box");

        VBox body = new VBox(10);
        body.setStyle("-fx-padding: 18 18 16 18;");
        Label name = new Label(food.getName());
        name.getStyleClass().add("food-name");
        name.setWrapText(true);
        Label desc = new Label(food.getDescription());
        desc.getStyleClass().add("food-description");
        desc.setWrapText(true);
        Label price = new Label(food.getPriceText());
        price.getStyleClass().add("food-price");

        if (featured) {
            Button book = new Button("Đặt ngay");
            book.getStyleClass().add("primary-button");
            book.setGraphic(IconUtil.create("fas-calendar-alt", "#FFFFFF", 15));
            book.setGraphicTextGap(8);
            book.setOnAction(event -> App.showBooking());
            HBox row = new HBox(12, price, new Region(), book);
            HBox.setHgrow(row.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);
            body.getChildren().addAll(name, desc, row);
        } else {
            Label category = new Label(food.getCategory().toUpperCase());
            category.getStyleClass().addAll("status-pill", "pill-info");
            HBox row = new HBox(12, price, new Region(), category);
            HBox.setHgrow(row.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);
            body.getChildren().addAll(name, desc, row);
        }

        card.getChildren().addAll(imageBox, body);
        return card;
    }
    @FXML
    private void searchforCategory(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String category = (String) clickedButton.getUserData();
        if (category.equals("all")) {
            renderFoods();
        }
        else {
        AppExecutor.getExecutor().submit(() -> {
            var foods = AppConfig.getInstance().getFoodService().getFoodsByCategory(category);
            Platform.runLater(() -> {
                foodGrid.getChildren().clear();
                for (Food food : foods) {
                    if(!food.getDelete()){
                        foodGrid.getChildren().add(createFoodCard(food, false));
                    }
                    
                }
            });
        });
    }
    }
}
