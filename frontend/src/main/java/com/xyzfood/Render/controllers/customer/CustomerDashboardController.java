package com.xyzfood.Render.controllers.customer;

import com.xyzfood.Render.App;
import com.xyzfood.Render.config.AppConfig;

import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.application.Platform;
import com.xyzfood.Render.utils.AppExecutor;

public class CustomerDashboardController {
    @FXML private TilePane featuredFoodGrid;

    @FXML
    private void initialize() {
        AppExecutor.getExecutor().submit(() -> {
            var foods = AppConfig.getInstance().getFoodService().getFoods();
            Platform.runLater(() -> {
                featuredFoodGrid.getChildren().clear();
                foods.stream()
                        .limit(3)
                        .map(food -> CartController.createFoodCard(food, true))
                        .forEach(featuredFoodGrid.getChildren()::add);
            });
        });
    }

    @FXML private void openBooking() { App.showBooking(); }
    @FXML private void openCart() { App.showCart(); }
}
