package com.xyzfood.Render.controllers.admin;

import com.xyzfood.Render.App;
import com.xyzfood.Render.config.AppConfig;
import com.xyzfood.Render.dto.Response.APIResponse;
import com.xyzfood.Render.models.Food;
import com.xyzfood.Render.utils.IconUtil;
import com.xyzfood.Render.utils.ToastUtil;
import com.xyzfood.Render.utils.AppExecutor;
import javafx.application.Platform;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class AdminCartController {
    @FXML private TilePane foodGrid;

    @FXML
    private void initialize() {
        renderFoods();
    }

    private void renderFoods() {
        foodGrid.getChildren().clear();
        AppExecutor.getExecutor().submit(() -> {
            List<Food> foods = AppConfig.getInstance().getFoodService().getFoods();
            Platform.runLater(() -> {
                for (Food food : foods) {
                    if(!food.getDelete()){
                        foodGrid.getChildren().add(createFoodCard(food));
                    }
                }
            });
        });
    }

    private VBox createFoodCard(Food food) {
        VBox card = new VBox();
        card.getStyleClass().add("food-card");

        Image image = new Image(food.getImage_path(), 250, 180, true, true, true);
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
        Label status = new Label("ĐANG BÁN");
        status.getStyleClass().addAll("status-pill", "pill-success");
        Label category = new Label(food.getCategory());
        category.getStyleClass().add("food-category");

        HBox priceRow = new HBox(10, price, new Region(), status);
        HBox.setHgrow(priceRow.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);

        HBox actions = new HBox(10);
        Button edit = new Button("Sửa");
        edit.setGraphic(IconUtil.create("fas-edit", "#60A5FA", 14));
        edit.setGraphicTextGap(6);
        edit.getStyleClass().addAll("ghost-button", "food-action-button");
        edit.setOnAction(e -> App.showFood(food));

        Button delete = new Button("Xóa");
        delete.setGraphic(IconUtil.create("fas-trash-alt", "#FFFFFF", 14));
        delete.setGraphicTextGap(6);
        delete.getStyleClass().addAll("danger-button", "food-action-button");
        delete.setOnAction(e -> deleteFood(food));
        actions.getChildren().addAll(edit, delete);

        body.getChildren().addAll(name, desc, priceRow, category, actions);
        card.getChildren().addAll(imageBox, body);
        return card;
    }

    @FXML
    private void handleAddFood() {
        App.showFood();
    }

    private void deleteFood(Food food) {
        
        AppExecutor.getExecutor().submit(() -> {
            APIResponse response = AppConfig.getInstance().getFoodService().deleteFood(food.getName());
            Platform.runLater(() -> {
                ToastUtil.show(response.getMessage());
                if (response.getSuccess()) {
                    renderFoods();
                }
            });
        });
    }
}
